package com.marvis.primeloan.data.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthRequest {
    private String username;
    private String password;
}
