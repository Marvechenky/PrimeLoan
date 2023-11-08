package com.marvis.primeloan.data.repositories;

import com.marvis.primeloan.data.model.LoanOfficer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanOfficerRepository extends JpaRepository<LoanOfficer, Long> {

    Optional<LoanOfficer> findByEmployeeId(String EmployeeId);
}
