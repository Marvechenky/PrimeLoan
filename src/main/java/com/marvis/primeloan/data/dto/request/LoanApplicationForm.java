package com.marvis.primeloan.data.dto.request;

import com.marvis.primeloan.data.model.enums.RepaymentPreference;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class LoanApplicationForm {

    private Long id;

    private BigDecimal amount;

    private String purpose;

    private RepaymentPreference repaymentPreference;

    private String duration;

}
