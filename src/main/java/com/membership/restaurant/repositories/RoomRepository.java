package com.membership.restaurant.repositories;

import com.membership.restaurant.entities.Hotel;
import com.membership.restaurant.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findRoomsByRoomName(String name);

    Room getRoomByRoomId(String roomId);

    List<Room> findRoomsByRoomNameAndRoomType(String room_name, String room_type);

    List<Room> findRoomsByHotel(Hotel hotel);

    @Transactional
    @Modifying
    void deleteRoomsByHotel(Hotel hotel);

    @Transactional
    @Modifying
    void deleteRoomByRoomId(String roomId);
}