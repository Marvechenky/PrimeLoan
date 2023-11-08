package com.marvis.primeloan.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;


@Builder
@Getter
@Setter
public class ApiResponse {

    private Object data;
    private ZonedDateTime timeStamp;
    private String path;

}
