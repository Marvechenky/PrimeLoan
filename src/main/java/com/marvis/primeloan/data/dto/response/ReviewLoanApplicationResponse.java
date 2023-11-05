package com.marvis.primeloan.data.dto.response;

import com.marvis.primeloan.data.model.Loan;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewLoanApplicationResponse {

    private HttpStatus httpStatus;
    private String message;
    private Loan loan;
}
