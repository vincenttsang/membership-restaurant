package com.membership.restaurant.dtos.responses;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class GetHotelRoomName {
    private String name;
    private String room_intro;
    private String room_image;
    private List<GetHotelRoom> roomList;

    public GetHotelRoomName() {
        roomList = new LinkedList<>();
    }

    public void initRoomList(List<GetHotelRoom> rooms) {
        for (GetHotelRoom room : rooms) {
            if (room.getRoom_name().equals(this.name)) {
                roomList.add(room);
            }
        }
        this.room_intro = roomList.get(0).getRoom_intro();
        this.room_image = roomList.get(0).getRoom_image();
    }
}
