package com.marvis.primeloan.utils;

import org.springframework.http.HttpStatus;

public class GenerateApiResponse {

    public static ApiResponse createdResponse(Object data) {
        return ApiResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .isSuccessful(true)
                .httpStatus(HttpStatus.CREATED)
                .data(data)
                .build();
    }

    public static ApiResponse okResponse(Object data) {
        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .httpStatus(HttpStatus.OK)
                .data(data)
                .build();
    }
}
