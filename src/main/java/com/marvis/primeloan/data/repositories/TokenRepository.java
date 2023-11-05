package com.marvis.primeloan.data.repositories;

import com.marvis.primeloan.data.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findTokenByEmailIgnoreCase(String email);

    Optional<Token> findTokenByJwt(String jwt);
}
