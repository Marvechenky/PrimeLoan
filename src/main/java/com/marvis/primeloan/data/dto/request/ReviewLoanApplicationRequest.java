package com.marvis.primeloan.data.dto.request;
import com.marvis.primeloan.data.model.enums.RepaymentPreference;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ReviewLoanApplicationRequest {

    private Long id;
    private BigDecimal amount;
    private String purpose;
    private RepaymentPreference repaymentPreference;


}
