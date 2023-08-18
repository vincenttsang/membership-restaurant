package com.membership.restaurant.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoomState {
    IS_AVAILABLE(0),
    IS_BOOKED(1),
    IS_CHECKED_IN(2);

    private final int value;

    RoomState(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }
}
