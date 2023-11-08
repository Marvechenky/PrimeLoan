package com.marvis.primeloan.service;

import com.marvis.primeloan.data.dto.request.LoanApplicationRequest;
import com.marvis.primeloan.data.dto.request.LoginRequest;
import com.marvis.primeloan.data.dto.request.RegistrationRequest;
import com.marvis.primeloan.data.dto.request.ViewLoanApplicationRequest;
import com.marvis.primeloan.data.dto.response.RegistrationResponse;
import com.marvis.primeloan.data.dto.response.Response;
import com.marvis.primeloan.data.dto.response.ViewLoanApplicationStatus;
import com.marvis.primeloan.data.model.*;
import com.marvis.primeloan.data.model.enums.LoanApplicationStatus;
import com.marvis.primeloan.data.model.enums.Role;
import com.marvis.primeloan.data.repositories.AddressRepository;
import com.marvis.primeloan.data.repositories.CustomerRepository;
import com.marvis.primeloan.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final LoanService loanService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegistrationResponse register(RegistrationRequest registrationRequest) {
        try {
            Customer customer = getCustomerPersonalDetails(registrationRequest);
//            Address address = getCustomerAddress(registrationRequest);
//            addressRepository.save(address);
           var savedCustomer= customerRepository.save(customer);
            return RegistrationResponse.builder()
                    .id(savedCustomer.getId())
                    .message("Registration successful")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            return RegistrationResponse.builder()
                    .message("Email does not exist")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }


    private Customer getCustomerPersonalDetails(RegistrationRequest registrationRequest) {
        Customer customer = new Customer();
        AppUser appUser = new AppUser();
        customer.setCreatedAt(LocalDateTime.now());
        appUser.setFirstName(registrationRequest.getFirstName());
        appUser.setOtherName(registrationRequest.getOtherName());
        appUser.setLastName(registrationRequest.getLastName());
        customer.setGender(registrationRequest.getGender());
        customer.setBirthDay(registrationRequest.getBirthDay());
        appUser.setPhoneNumber(registrationRequest.getPhoneNumber());
        appUser.setEmail(registrationRequest.getEmail());
        appUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        customer.setAddress(getCustomerAddress(registrationRequest));
        appUser.setRole(Set.of(Role.CUSTOMER));
        customer.setAppUser(appUser);
        return customer;
    }

    private Address getCustomerAddress(RegistrationRequest registrationRequest) {
        Address address = new Address();
        address.setHouseNumber(registrationRequest.getAddress().getHouseNumber());
        address.setStreetName(registrationRequest.getAddress().getStreetName());
        address.setCity(registrationRequest.getAddress().getCity());
        address.setPostalCode(registrationRequest.getAddress().getPostalCode());
        address.setState(registrationRequest.getAddress().getState());
        address.setCountry(registrationRequest.getAddress().getCountry());
        return address;
    }
    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findCustomerByEmailIgnoreCase(String email) {
        return customerRepository.findByAppUser_Email(email);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Response applyForLoan(LoanApplicationRequest loanApplicationRequest) {
        try {
            Customer customer = customerRepository.findById(loanApplicationRequest.getId())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer with does not exist"));

            Optional<Loan> loanFound = loanService.findLoan(customer.getId());
            if (loanFound.isPresent()) throw new RuntimeException("You have already applied for loan");
            Loan loan = buildLoan(loanApplicationRequest);
            loan.setCustomer(customer);
            loanService.save(loan);
            customer.setLoan(loan);
            customerRepository.save(customer);
            return response(HttpStatus.OK, "Loan application successful");
        } catch (Exception e) {
            return response(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ViewLoanApplicationStatus viewLoanApplicationStatus(ViewLoanApplicationRequest viewLoanApplicationRequest) {

        try {
            Customer customer = customerRepository.findById(viewLoanApplicationRequest.getId())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer does not exist"));
            return ViewLoanApplicationStatus.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Operation successful")
                    .loanApplicationStatus(customer.getLoan().getLoanApplicationStatus())
                    .reason(customer.getLoan().getReasonForRejection())
                    .build();
        } catch (Exception e) {
            return ViewLoanApplicationStatus.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build();
        }
    }


    private Loan buildLoan(LoanApplicationRequest loanApplicationRequest) {
        return Loan.builder()
                .amount(loanApplicationRequest.getAmount())
                .purpose(loanApplicationRequest.getPurpose())
                .repaymentPreference(loanApplicationRequest.getRepaymentPreference())
                .loanApplicationStatus(LoanApplicationStatus.IN_PROGRESS)
                .build();
    }


    private Response response(HttpStatus httpStatus, String message) {
        return Response.builder()
                .status(httpStatus)
                .message(message)
                .build();
    }


    @Override
    public Optional<LoanAgreement> viewLoanAgreement(Long id) {
        return loanService.viewLoanAgreement(id);
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        return null;
    }


}
