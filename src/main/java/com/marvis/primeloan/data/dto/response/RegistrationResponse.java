package com.marvis.primeloan.data.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationResponse {
    private Long id;
    private HttpStatus httpStatus;
    private String message;
}
