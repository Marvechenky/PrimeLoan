package com.marvis.primeloan.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class ApiResponse {

    private ZonedDateTime timeStamp;
    private int statusCode;
    private String path;
    private Object data;
    private Boolean isSuccessful;
}
