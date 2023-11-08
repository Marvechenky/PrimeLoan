package com.marvis.primeloan.service;

import com.marvis.primeloan.config.JwtService;
import com.marvis.primeloan.data.dto.request.*;
import com.marvis.primeloan.data.dto.response.AuthResponse;
import com.marvis.primeloan.data.dto.response.Response;
import com.marvis.primeloan.data.dto.response.ReviewLoanApplicationResponse;
import com.marvis.primeloan.data.model.*;
import com.marvis.primeloan.data.model.enums.LoanApplicationStatus;
import com.marvis.primeloan.data.model.enums.Role;
import com.marvis.primeloan.data.repositories.LoanOfficerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoanOfficerServiceImpl implements LoanOfficerService{

    private final LoanOfficerRepository loanOfficerRepository;
    private final LoanService loanService;
    private final Authentication authentication;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    private final CustomerService customerService;

    @Override
    public List<Loan> viewLoanApplications() {
        return loanService.viewLoanApplications();
    }

    @Override
    public ReviewLoanApplicationResponse reviewLoanApplicationDetails
            (ReviewLoanApplicationRequest loanApplicationRequest) {

        try {
            Loan loan = loanService.reviewLoanApplication(loanApplicationRequest.getId())
                    .orElseThrow(() -> new RuntimeException("No loan application available"));
            return ReviewLoanApplicationResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Operation successful")
                    .loan(loan)
                    .build();
        } catch (Exception e) {
            return ReviewLoanApplicationResponse.builder()
                    .message(e.getMessage())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

    }

    @Override
    public Response approveLoanApplication
            (ViewLoanApplicationRequest viewLoanApplicationStatus) {
        try {
            Loan loan = loanService.findLoan(viewLoanApplicationStatus.getId())
                    .orElseThrow(() -> new RuntimeException("No loan application available"));
           loan.setLoanApplicationStatus(LoanApplicationStatus.APPROVED);
           loanService.save(loan);
           return response(HttpStatus.OK, "Loan application approved");
        } catch (Exception e) {
            return response(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Response rejectLoanApplication
            (RejectLoanApplicationRequest rejectLoanApplicationRequest) {
        try {
            Loan loan = loanService.findLoan(rejectLoanApplicationRequest.getId())
                    .orElseThrow(() -> new RuntimeException("No loan application available"));
            loan.setLoanApplicationStatus(LoanApplicationStatus.REJECTED);
            loan.setReasonForRejection(rejectLoanApplicationRequest.getReasonForRejection());
            loanService.save(loan);
            return response(HttpStatus.OK, "Loan application rejected");
        } catch (Exception e) {
            return response(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @Override
    public Response generateLoanAgreement
            (GenerateLoanAgreementRequest generateLoanAgreementRequest) {

        try {
            Customer customer = customerService.findCustomerByEmailIgnoreCase(generateLoanAgreementRequest.getCustomerEmail())
                    .orElseThrow(() -> new RuntimeException("Customer does exist"));
            if (!(customer.getLoan().getLoanApplicationStatus().equals(LoanApplicationStatus.APPROVED))) {
                throw new RuntimeException("This customer's loan application is not approved");
            }
            Loan loan = loanService.findLoan(customer.getId())
                    .orElseThrow(() -> new RuntimeException("No customer with details found"));
            LoanAgreement loanAgreement = buildLoanAgreement(generateLoanAgreementRequest, customer, loan);
            loanService.saveLoanAgreement(loanAgreement);
            return response(HttpStatus.OK, "Loan agreement generated successfully");
        } catch (Exception e) {
            return response(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private LoanAgreement buildLoanAgreement
            (GenerateLoanAgreementRequest generateLoanAgreementRequest, Customer customer, Loan loan) {
        return LoanAgreement.builder()
                .id(customer.getId())
                .borrowerName(customer.getAppUser().getFirstName() + " "
                        + customer.getAppUser().getOtherName() + " " + customer.getAppUser().getLastName())
                .customerEmail(generateLoanAgreementRequest.getCustomerEmail())
                .borrowerPhoneNumber(customer.getAppUser().getPhoneNumber())
                .lenderName(generateLoanAgreementRequest.getLenderName())
                .loanAmount(loan.getAmount())
                .loanDuration(generateLoanAgreementRequest.getLoanDuration())
                .interestRate(generateLoanAgreementRequest.getInterestRate())
                .loanTerms(generateLoanAgreementRequest.getLoanTerms())
                .repaymentSchedule(generateLoanAgreementRequest.getRepaymentSchedule())
                .paymentMode(generateLoanAgreementRequest.getPaymentMode())
                .loanOfficer(loan.getLoanOfficer())
                .build();
    }

    @Override
    public Response updateLoanApplicationStatus
            (UpdateLoanApplicationStatusRequest updateLoanApplicationStatusRequest) {

        try {
            Customer customer = customerService.findCustomerById(updateLoanApplicationStatusRequest.getId())
                    .orElseThrow( () -> new RuntimeException("Customer with id is unavailable"));
            customer.getLoan().setLoanApplicationStatus(updateLoanApplicationStatusRequest.getLoanApplicationStatus());
            loanService.save(customer.getLoan());
            customerService.save(customer);
            return response(HttpStatus.OK, "Operation successful");
        } catch (Exception e){
            return response(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @Override
    public AuthResponse authenticateAndGetToken(AuthRequest authRequest) {

        try{
            String token;
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                            authRequest.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            if (authentication.isAuthenticated()) token = jwtService.generateToken(userDetails);
            else throw new IllegalArgumentException("User not found");
            return AuthResponse.builder()
                    .message("Authentication successful")
                    .httpStatus(HttpStatus.OK)
                    .token(token)
                    .build();
        } catch (Exception e) {
            return AuthResponse.builder()
                    .message("Invalid username or password")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

    }

    private Response response(HttpStatus httpStatus, String message) {
        return Response.builder()
                .status(httpStatus)
                .message(message)
                .build();
    }

    @PostConstruct
    private void createLoanOfficer() {
        if (loanOfficerExist("loanOfficer001")) {
            LoanOfficer loanOfficer = LoanOfficer.builder()
                    .employeeId("LoanOfficer001")
                    .appUser(AppUser.builder()
                            .role(Set.of(Role.LOAN_OFFICER))
                            .email("loanOfficer@gmail.com")
                            .password(passwordEncoder.encode("loanOfficer001"))
                            .build())
                    .build();
            loanOfficerRepository.save(loanOfficer);
        }
    }

    private boolean loanOfficerExist(String employeeId) {
        Optional<LoanOfficer> loanOfficer = loanOfficerRepository.findByEmployeeId(employeeId);
        return loanOfficer.isPresent();
    }
}
