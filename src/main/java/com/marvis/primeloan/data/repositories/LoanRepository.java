package com.marvis.primeloan.data.repositories;

import com.marvis.primeloan.data.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
