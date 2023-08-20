package com.membership.restaurant.dtos.responses;

import com.membership.restaurant.dtos.responses.HotelDetailElement;
import lombok.Data;

import java.util.List;

@Data
public class HotelDetailData {
    private List<HotelDetailElement> hotel;
}
