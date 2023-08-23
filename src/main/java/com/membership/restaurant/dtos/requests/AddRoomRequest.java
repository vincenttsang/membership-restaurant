package com.membership.restaurant.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddRoomRequest {
    private int bookerNum;
    @NotNull
    private String roomDetail;
    @NotNull
    private String roomImage;
    @NotNull
    private String roomIntro;
    @NotNull
    private String roomName;

    private int roomNum;

    private BigDecimal roomPrice;
    @NotNull
    private String roomType;
}
