package com.marvis.primeloan.service;

import com.marvis.primeloan.data.model.Loan;
import com.marvis.primeloan.data.model.LoanAgreement;
import com.marvis.primeloan.data.repositories.LoanAgreementRepository;
import com.marvis.primeloan.data.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;
    private final LoanAgreementRepository loanAgreementRepository;

    @Override
    public Optional<Loan> findLoan(Long id) {
        return loanRepository.findById(id);
    }

    @Override
    public List<Loan> viewLoanApplications() {
        return loanRepository.findAll();
    }

    @Override
    public Optional<Loan> reviewLoanApplication(Long id) {
        return loanRepository.findById(id);
    }

    @Override
    public Optional<LoanAgreement> viewLoanAgreement(Long id) {
        return loanAgreementRepository.findById(id);
    }

    @Override
    public void save(Loan loan) {
        loanRepository.save(loan);

    }

    @Override
    public void saveLoanAgreement(LoanAgreement loanAgreement) {
        loanAgreementRepository.save(loanAgreement);
    }
}
