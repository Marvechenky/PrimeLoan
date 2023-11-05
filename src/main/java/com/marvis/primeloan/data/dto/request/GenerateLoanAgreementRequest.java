package com.marvis.primeloan.data.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateLoanAgreementRequest {

    private String customerEmail;
    private String loanTerms;
    private String loanDuration;
    private String interestRate;
    private String repaymentSchedule;
    private String paymentMode;
    private String lenderName;
}
