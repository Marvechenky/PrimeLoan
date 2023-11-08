package com.marvis.primeloan.utils;

import org.springframework.http.HttpStatus;

public class GenerateApiResponse {

    public static ApiResponse createdResponse(Object data) {
        return ApiResponse.builder()
                .data(data)
                .build();
    }

    public static ApiResponse okResponse(Object data) {
        return ApiResponse.builder()
                .data(data)
                .build();
    }
}
