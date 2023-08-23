package com.membership.restaurant.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddRoomRequest {
    @JsonProperty(value = "booker_num")
    private int bookerNum;

    @NotNull
    @JsonProperty(value = "room_detail")
    private String roomDetail;

    @NotNull
    @JsonProperty(value = "room_image")
    private String roomImage;

    @NotNull
    @JsonProperty(value = "room_intro")
    private String roomIntro;

    @NotNull
    @JsonProperty(value = "room_name")
    private String roomName;

    @JsonProperty(value = "room_num")
    private int roomNum;

    @JsonProperty(value = "room_price")
    private BigDecimal roomPrice;

    @NotNull
    @JsonProperty(value = "room_type")
    private String roomType;
}
