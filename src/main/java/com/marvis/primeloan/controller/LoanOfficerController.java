package com.marvis.primeloan.controller;

import com.marvis.primeloan.data.dto.request.AuthRequest;
import com.marvis.primeloan.service.LoanOfficerService;
import com.marvis.primeloan.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/loan-officer")
@RequiredArgsConstructor
public class LoanOfficerController {

    private LoanOfficerService loanOfficerService;


}
