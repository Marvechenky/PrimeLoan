package com.marvis.primeloan.service;

import com.marvis.primeloan.data.dto.request.LoanApplicationForm;
import com.marvis.primeloan.data.dto.request.RegistrationRequest;
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
            Address address = getCustomerAddress(registrationRequest);
            addressRepository.save(address);
            customerRepository.save(customer);
            return RegistrationResponse.builder()
                    .id(customer.getId())
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
        customer.setFirstName(registrationRequest.getFirstName());
        customer.setOtherName(registrationRequest.getOtherName());
        customer.setLastName(registrationRequest.getLastName());
        customer.setGender(registrationRequest.getGender());
        customer.setBirthDay(registrationRequest.getBirthDay());
        customer.setPhoneNumber(registrationRequest.getPhoneNumber());
        customer.setEmail(registrationRequest.getEmail());
        customer.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        customer.setRole(Set.of(Role.CUSTOMER));
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
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Response applyForLoan(LoanApplicationForm loanApplicationForm) {
        try {
            Customer customer = customerRepository.findById(loanApplicationForm.getId())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer with does not exist"));

            Optional<Loan> loanFound = loanService.findLoan(customer.getId());
            if (loanFound.isPresent()) throw new RuntimeException("You have already applied for loan");
            Loan loan = buildLoan(loanApplicationForm);
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
    public ViewLoanApplicationStatus viewLoanApplicationStatus(LoanApplicationForm loanApplicationForm) {
        try {
            Customer customer = customerRepository.findById(loanApplicationForm.getId())
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

    private Loan buildLoan(LoanApplicationForm loanApplicationForm) {
        return Loan.builder()
                .amount(loanApplicationForm.getAmount())
                .purpose(loanApplicationForm.getPurpose())
                .repaymentPreference(loanApplicationForm.getRepaymentPreference())
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
}
