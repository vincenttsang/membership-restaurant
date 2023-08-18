package com.membership.restaurant.dtos.requests;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    /**
     * 新密码，事一个一个MD5格式的密码
     */
    private String newPassword;
    /**
     * 旧密码，事一个一个MD5格式的密码
     */
    private String oldPassword;
}
