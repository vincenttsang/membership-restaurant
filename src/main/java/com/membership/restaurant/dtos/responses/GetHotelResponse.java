package com.membership.restaurant.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class GetHotelResponse {
    private String name;
    private String address;
    private String tel;
    private String intro;
    private String image;
    private List<GetHotelRoomName> nameList;
}

