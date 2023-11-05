package com.marvis.primeloan.data.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RejectLoanApplicationRequest {
    private Long id;
    private String reasonForRejection;
}
