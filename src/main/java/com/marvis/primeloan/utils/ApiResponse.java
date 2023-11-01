package com.marvis.primeloan.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Builder
@Getter
@Setter
public class ApiResponse {

    private Object data;
    private HttpStatus httpStatus;
    private int statusCode;
    private Boolean isSuccessful;
}
