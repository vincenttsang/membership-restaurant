package com.membership.restaurant.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LoginSession {
    private String session_id;
    private int user_id;
    private long expirationTime;

    public LoginSession(int user_id) {
        this.session_id = String.valueOf(UUID.randomUUID());
        this.expirationTime = System.currentTimeMillis() + (1000 * 60 * 60 * 3); // 3 hrs
        this.user_id = user_id;
    }

    public LoginSession() {
        this.session_id = String.valueOf(UUID.randomUUID());
        this.expirationTime = System.currentTimeMillis() + (1000 * 60 * 60 * 3); // 3 hrs
        this.user_id = -1;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime;
    }
}