package com.marvis.primeloan.service;
import com.marvis.primeloan.data.dto.request.LoanApplicationRequest;
import com.marvis.primeloan.data.dto.request.LoginRequest;
import com.marvis.primeloan.data.dto.request.RegistrationRequest;
import com.marvis.primeloan.data.dto.request.ViewLoanApplicationRequest;
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

    Response applyForLoan(LoanApplicationRequest loanApplicationRequest);

    ViewLoanApplicationStatus viewLoanApplicationStatus(ViewLoanApplicationRequest viewLoanApplicationRequest);

    Optional<LoanAgreement> viewLoanAgreement(Long id);

    Response login(LoginRequest loginRequest);
}
