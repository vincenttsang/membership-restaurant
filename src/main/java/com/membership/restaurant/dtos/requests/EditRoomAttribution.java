package com.membership.restaurant.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EditRoomAttribution {
    @NotNull
    @JsonProperty(value = "bookerNum")
    private int bookerNum;

    @NotNull
    @JsonProperty(value = "room_detail")
    private String roomDetail;

    @NotNull
    @JsonProperty(value = "room_type")
    private String roomType;

    @JsonProperty(value = "new_room_type")
    private String newRoomType;

    @NotNull
    @JsonProperty(value = "room_price")
    private BigDecimal roomPrice;
}
