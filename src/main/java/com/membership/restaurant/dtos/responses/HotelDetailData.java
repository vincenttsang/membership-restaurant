package com.membership.restaurant.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class HotelDetailData {
    private List<HotelDetailElement> hotel;
}
