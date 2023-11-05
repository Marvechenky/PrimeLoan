package com.marvis.primeloan.data.repositories;

import com.marvis.primeloan.data.model.LoanAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanAgreementRepository extends JpaRepository<LoanAgreement, Long> {
    Optional<LoanAgreement> findById(Long id);
}
