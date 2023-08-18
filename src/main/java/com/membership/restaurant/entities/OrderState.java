package com.membership.restaurant.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderState {
    IS_PAID(0),
    IS_CANCELED(1),
    IS_USING(2),
    IS_DONE(3);

    private final int value;

    OrderState(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }
}
