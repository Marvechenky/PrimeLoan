package com.marvis.primeloan.service;

import com.marvis.primeloan.data.dto.request.*;
import com.marvis.primeloan.data.dto.response.Response;
import com.marvis.primeloan.data.dto.response.ReviewLoanApplicationResponse;
import com.marvis.primeloan.data.model.Customer;
import com.marvis.primeloan.data.model.Loan;
import com.marvis.primeloan.data.model.LoanAgreement;
import com.marvis.primeloan.data.model.enums.LoanApplicationStatus;
import com.marvis.primeloan.data.repositories.LoanOfficerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanOfficerServiceImpl implements LoanOfficerService{

    private final LoanOfficerRepository loanOfficerRepository;
    private final LoanService loanService;

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
            (ViewLoanApplicationRequest viewLoanApplicationRequest) {
        try {
            Loan loan = loanService.findLoan(viewLoanApplicationRequest.getId())
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
                .borrowerName(customer.getFirstName() + " "
                        + customer.getOtherName() + " " + customer.getLastName())
                .customerEmail(generateLoanAgreementRequest.getCustomerEmail())
                .borrowerPhoneNumber(customer.getPhoneNumber())
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


    private Response response(HttpStatus httpStatus, String message) {
        return Response.builder()
                .status(httpStatus)
                .message(message)
                .build();
    }
}
