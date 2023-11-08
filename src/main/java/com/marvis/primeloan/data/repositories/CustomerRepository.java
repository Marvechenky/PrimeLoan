package com.marvis.primeloan.data.repositories;

import com.marvis.primeloan.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findById(Long id);

    Optional<Customer> findByAppUser_Email(String email);
}
