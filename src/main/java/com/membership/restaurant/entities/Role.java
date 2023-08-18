package com.membership.restaurant.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ROOT(0),
    ADMIN(1),
    USER(2);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }
}
