package com.membership.restaurant.dtos.requests;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class UpdateUserRequest {
    /**
     * 生日
     */
    @Nullable
    private String birthday;
    /**
     * 电邮
     */
    @Nullable
    private String email;
    /**
     * 电话
     */
    @Nullable
    private String tel;
}
