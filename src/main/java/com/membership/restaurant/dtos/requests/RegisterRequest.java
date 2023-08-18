package com.membership.restaurant.dtos.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotEmpty
    private String account;

    @NotEmpty
    private String password;

    @Nullable
    private String tel;
}
