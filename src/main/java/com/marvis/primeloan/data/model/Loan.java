package com.marvis.primeloan.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marvis.primeloan.data.model.enums.LoanApplicationStatus;
import com.marvis.primeloan.data.model.enums.RepaymentPreference;
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

    private String reasonForRejection;

    @Enumerated(value = EnumType.STRING)
    private RepaymentPreference repaymentPreference;

    @Enumerated(value = EnumType.STRING)
    private LoanApplicationStatus loanApplicationStatus;

    @OneToOne
    @JoinColumn(name = "loan_id")
    private LoanAgreement loanAgreement;

    @OneToOne
    @JoinColumn(name = "customer_id")
    @JsonProperty
    private Customer customer;

    @OneToOne
    private LoanOfficer loanOfficer;
}
