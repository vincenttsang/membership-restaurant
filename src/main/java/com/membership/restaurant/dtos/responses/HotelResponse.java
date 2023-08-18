package com.membership.restaurant.dtos.responses;

import lombok.Data;

@Data
public class HotelResponse {
    private String area;
    private long consumer;
    private long id;
    private String intro;
    private String name;
    private String picture;
    private long price;
    private long roomCount;
    private long totalMoney;
}
