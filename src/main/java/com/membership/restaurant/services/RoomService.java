package com.membership.restaurant.services;

import com.membership.restaurant.dtos.requests.EditRoomAttribution;
import com.membership.restaurant.dtos.requests.EditRoomRequest;
import com.membership.restaurant.entities.Hotel;
import com.membership.restaurant.entities.Room;
import com.membership.restaurant.repositories.OrderFormRepository;
import com.membership.restaurant.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final OrderFormRepository orderFormRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository,
                       OrderFormRepository orderFormRepository) {
        this.roomRepository = roomRepository;
        this.orderFormRepository = orderFormRepository;
    }

    public List<Room> getRoomsByHotel(Hotel hotel) {
        return roomRepository.findRoomsByHotel(hotel);
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    public void deleteRoom(String roomId) {
        orderFormRepository.deleteOrderFormsByRoomId(this.getRoomByRoomId(roomId));
        roomRepository.deleteRoomByRoomId(roomId);
    }

    public void editRooms(EditRoomRequest editRoomRequest) {
        String roomName = editRoomRequest.getRoomName();
        List<EditRoomAttribution> roomAttributions = editRoomRequest.getRoomAttribution();
        List<Room> allRooms = this.getRooms();

        for (Room room : allRooms) {
            if (room.getRoomName().equals(editRoomRequest.getRoomName())) {
                for (EditRoomAttribution roomAttribution : roomAttributions) {
                    if (roomAttribution.getRoomType().equals(room.getRoomType())) {
                        room.setRoomPrice(roomAttribution.getRoomPrice());
                        room.setBookerNum(roomAttribution.getBookerNum());
                        room.setRoomDetail(roomAttribution.getRoomDetail());
                        if (roomAttribution.getNewRoomType() != null) {
                            room.setRoomType(roomAttribution.getNewRoomType());
                        }
                        this.saveRoom(room);
                    }
                }
            }
        }
    }

    public Room getRoomByRoomId(String roomId) {
        return roomRepository.getRoomByRoomId(roomId);
    }
}
