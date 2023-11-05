package com.marvis.primeloan.data.dto.response;

import com.marvis.primeloan.data.model.enums.LoanApplicationStatus;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewLoanApplicationStatus {

    private HttpStatus httpStatus;
    private String message;
    private LoanApplicationStatus loanApplicationStatus;
    private String reason;
}
