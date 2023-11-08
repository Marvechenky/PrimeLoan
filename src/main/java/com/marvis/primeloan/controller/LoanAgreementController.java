package com.marvis.primeloan.controller;
import com.marvis.primeloan.service.LoanService;
import com.marvis.primeloan.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/loan-agreement")
@RequiredArgsConstructor
public class LoanAgreementController {

    private final LoanService loanService;


    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<ApiResponse> viewLoanAgreement(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        ApiResponse response = ApiResponse.builder()
                .data(loanService.viewLoanAgreement(id))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
