package com.marvis.primeloan.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String borrowerName;
    private String borrowerPhoneNumber;
    private String customerEmail;
    private BigDecimal loanAmount;
    private String interestRate;
    private String loanDuration;
    private String lenderName;
    private String paymentMode;
    private String loanTerms;
    private String repaymentSchedule;
    @OneToOne
    private LoanOfficer loanOfficer;

}
