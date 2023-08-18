package com.membership.restaurant.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    private String account;
    @NotNull
    private String password;
}
