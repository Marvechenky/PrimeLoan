package com.marvis.primeloan.data.model;

import com.marvis.primeloan.enums.LoanStatus;
import com.marvis.primeloan.enums.RepaymentPreference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String purpose;

    @Enumerated(value = EnumType.STRING)
    private RepaymentPreference repaymentPreference;

    @Enumerated(value = EnumType.STRING)
    private LoanStatus loanStatus;

    @Embedded
    private Agreement loanAgreement;
}
