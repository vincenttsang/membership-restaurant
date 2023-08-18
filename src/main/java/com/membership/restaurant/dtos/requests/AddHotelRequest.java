package com.membership.restaurant.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddHotelRequest {
    @NotNull
    private String area;
    @NotNull
    private String intro;
    @NotNull
    private String name;
    @NotNull
    private String picture;
    @NotNull
    private String tel;
    @NotNull
    private String address;
}
