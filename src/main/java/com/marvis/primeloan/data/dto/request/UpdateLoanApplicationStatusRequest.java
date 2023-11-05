package com.marvis.primeloan.data.dto.request;

import com.marvis.primeloan.data.model.enums.LoanApplicationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateLoanApplicationStatusRequest {
    private Long id;
    private LoanApplicationStatus loanApplicationStatus;
}
