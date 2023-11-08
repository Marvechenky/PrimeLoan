package com.marvis.primeloan.controller;

import com.marvis.primeloan.data.dto.request.*;
import com.marvis.primeloan.service.CustomerService;
import com.marvis.primeloan.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;



    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegistrationRequest registrationRequest,
                                                HttpServletRequest httpServletRequest) {
        ApiResponse response = ApiResponse.builder()
                .data(customerService.register(registrationRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest,
                                             HttpServletRequest httpServletRequest) {
        ApiResponse response = ApiResponse.builder()
                .data(customerService.login(loginRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("loan")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<ApiResponse> applyForLoan(@RequestBody LoanApplicationRequest loanApplicationRequest,
                                                    HttpServletRequest httpServletRequest) {
        ApiResponse response = ApiResponse.builder()
                .data(customerService.applyForLoan(loanApplicationRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("loan-status")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<ApiResponse> viewLoanApplicationStatus(@RequestBody ViewLoanApplicationRequest viewLoanApplicationRequest,
                                                                 HttpServletRequest httpServletRequest) {
        ApiResponse response = ApiResponse.builder()
                .data(customerService.viewLoanApplicationStatus(viewLoanApplicationRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
