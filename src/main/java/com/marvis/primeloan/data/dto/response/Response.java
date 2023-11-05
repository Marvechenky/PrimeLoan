package com.marvis.primeloan.data.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private HttpStatus status;
    private String message;
}
