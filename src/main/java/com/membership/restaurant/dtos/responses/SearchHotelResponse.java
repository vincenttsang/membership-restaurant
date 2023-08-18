package com.membership.restaurant.dtos.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SearchHotelResponse {
    private String address;
    private Integer id;
    private String intro;
    private String name;
    private String picture;
    private BigDecimal price;
}
