package com.marvis.primeloan.data.dto.request;

import com.marvis.primeloan.data.model.enums.RepaymentPreference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class LoanApplicationRequest {

    private Long id;

    private BigDecimal amount;

    private String purpose;

    private RepaymentPreference repaymentPreference;

    private String duration;

}
