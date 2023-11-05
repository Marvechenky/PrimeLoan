package com.marvis.primeloan.service;

import com.marvis.primeloan.data.dto.request.LoanApplicationForm;
import com.marvis.primeloan.data.dto.request.RegistrationRequest;
import com.marvis.primeloan.data.dto.response.RegistrationResponse;
import com.marvis.primeloan.data.dto.response.Response;
import com.marvis.primeloan.data.dto.response.ViewLoanApplicationStatus;
import com.marvis.primeloan.data.model.Customer;
import com.marvis.primeloan.data.model.LoanAgreement;

import java.util.Optional;

public interface CustomerService {

    RegistrationResponse register(RegistrationRequest registrationRequest);
    Optional<Customer> findCustomerById(Long id);

    Optional<Customer> findCustomerByEmailIgnoreCase(String email);

    Customer save(Customer customer);

    Response applyForLoan(LoanApplicationForm loanApplicationForm);

    ViewLoanApplicationStatus viewLoanApplicationStatus(LoanApplicationForm loanApplicationForm);

    Optional<LoanAgreement> viewLoanAgreement(Long id);


}
