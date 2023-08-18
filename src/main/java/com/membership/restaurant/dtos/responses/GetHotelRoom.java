package com.membership.restaurant.dtos.responses;

import com.membership.restaurant.entities.Room;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GetHotelRoom {
    private int room_id;
    private String room_name;
    private String room_intro;
    private String room_type;
    private BigDecimal room_price;
    private String room_detail;
    private String room_image;
    private int booker_num;

    public GetHotelRoom() {

    }

    public GetHotelRoom(Room room) {
        this.room_id = Integer.valueOf(room.getRoomId());
        this.room_name = room.getRoomName();
        this.room_intro = room.getIntro();
        this.room_type = room.getRoomType();
        this.room_price = room.getRoomPrice();
        this.room_detail = room.getRoomDetail();
        this.booker_num = room.getBookerNum();
        this.room_image = room.getRoomImage();
    }
}
