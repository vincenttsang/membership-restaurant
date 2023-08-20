package com.membership.restaurant.dtos.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelDetailElement {
    private String area;
    private Integer consumer;
    private Integer id;
    private String intro;
    private String name;
    private String picture;
    private BigDecimal price;
    private Integer roomCount;
    private BigDecimal totalMoney;
}
