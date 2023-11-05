package com.marvis.primeloan.service;

import com.marvis.primeloan.data.model.Loan;
import com.marvis.primeloan.data.model.LoanAgreement;

import java.util.List;
import java.util.Optional;

public interface LoanService {

    Optional<Loan> findLoan(Long id);

    List<Loan> viewLoanApplications();

    Optional<Loan> reviewLoanApplication(Long id);

    Optional<LoanAgreement> viewLoanAgreement(Long id);

    void save(Loan loan);

    void saveLoanAgreement(LoanAgreement loanAgreement);
}
