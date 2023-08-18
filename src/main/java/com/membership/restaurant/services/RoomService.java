package com.membership.restaurant.services;

import com.membership.restaurant.entities.Hotel;
import com.membership.restaurant.entities.Room;
import com.membership.restaurant.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getRoomsByHotel(Hotel hotel) {
        return roomRepository.findRoomsByHotel(hotel);
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }
}
