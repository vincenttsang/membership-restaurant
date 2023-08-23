package com.membership.restaurant.services;

import com.membership.restaurant.dtos.requests.BookRequest;
import com.membership.restaurant.dtos.responses.SearchHotelResponse;
import com.membership.restaurant.entities.Hotel;
import com.membership.restaurant.entities.OrderForm;
import com.membership.restaurant.entities.OrderState;
import com.membership.restaurant.entities.Room;
import com.membership.restaurant.repositories.HotelRepository;
import com.membership.restaurant.repositories.OrderFormRepository;
import com.membership.restaurant.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class HotelService {
    HotelRepository hotelRepository;

    OrderFormRepository orderFormRepository;

    RoomRepository roomRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository, OrderFormRepository orderFormRepository, RoomRepository roomRepository) {
        this.orderFormRepository = orderFormRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @org.jetbrains.annotations.NotNull
    private static List<Room> getFilteredRooms(String area, LocalDate startDate, LocalDate endDate, String keywords, List<Room> rooms, List<OrderForm> orderForms) {
        List<Room> filteredRooms = new LinkedList<>();
        for (Room room : rooms) {
            boolean isAvailable = true;
            for (OrderForm orderForm : orderForms) {
                if (orderForm.getState() == OrderState.IS_DONE || orderForm.getState() == OrderState.IS_CANCELED) {

                } else if (((orderForm.getStartDate().isAfter(startDate) || orderForm.getStartDate().isEqual(startDate)) && (orderForm.getStartDate().isBefore(endDate) || orderForm.getStartDate().isEqual(endDate)))
                        && (orderForm.getRoomId().getId() == room.getId())) {
                    // room不可用
                    isAvailable = false;
                }
            }

            if (area != null) {
                if (!room.getHotel().getArea().equals(area)) {
                    isAvailable = false;
                }
            }

            if (keywords != null) {
                if (room.getIntro().contains(keywords) || room.getHotel().getIntro().contains(keywords)) {
                    // 含有关键词
                } else {
                    isAvailable = false;
                }
            }
            if (isAvailable) {
                filteredRooms.add(room);
            }
        }
        return filteredRooms;
    }

    public List<SearchHotelResponse> search(String area, LocalDate startDate, LocalDate endDate, String keywords) {
        if (startDate.isAfter(endDate)) {
            return new LinkedList<>();
        }

        List<OrderForm> orderForms = orderFormRepository.findAll();
        List<Room> rooms = roomRepository.findAll();
        List<SearchHotelResponse> responses = new LinkedList<>();

        List<Room> filteredRooms = getFilteredRooms(area, startDate, endDate, keywords, rooms, orderForms);

        for (Room availRoom : filteredRooms) {
            Hotel availHotel = availRoom.getHotel();
            SearchHotelResponse searchHotelResponse = new SearchHotelResponse();
            searchHotelResponse.setId(availHotel.getId());
            searchHotelResponse.setName(availHotel.getName());
            searchHotelResponse.setPicture(availHotel.getPicture());
            searchHotelResponse.setIntro(availHotel.getIntro());
            searchHotelResponse.setPrice(availRoom.getRoomPrice());
            searchHotelResponse.setAddress(availHotel.getAddress());
            responses.add(searchHotelResponse);
        }

        //去除重复酒店，并找到最便宜的房间
        Map<Integer, SearchHotelResponse> responseMap = new HashMap<>();
        for (SearchHotelResponse response : responses) {
            if (responseMap.containsKey(response.getId())) {
                SearchHotelResponse old = responseMap.get(response.getId());
                if (response.getPrice().compareTo(old.getPrice()) == -1) {
                    responseMap.remove(response.getId());
                    responseMap.put(response.getId(), response);
                }
            } else {
                responseMap.put(response.getId(), response);
            }
        }
        responses = new LinkedList<>(responseMap.values());

        return responses;
    }

    public void saveHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }


    public Hotel getHotel(int id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public void deleteHotel(int id) {
        orderFormRepository.deleteOrderFormsByHotel(this.getHotel(id));
        roomRepository.deleteRoomsByHotel(this.getHotel(id));
        hotelRepository.deleteHotelById(id);
    }

    public List<Room> getAvailableRoomsForBooking(BookRequest bookRequest) {
        List<OrderForm> orderForms = orderFormRepository.findAll();
        List<Room> allRooms = roomRepository.findAll();
        List<Room> rooms = new LinkedList<>();
        bookRequest.getRoomType();
        bookRequest.getRoomName();
        for (Room room : allRooms) {
            if (room.getHotel().getId() == bookRequest.getHotelId()) {
                if (room.getRoomName().equals(bookRequest.getRoomName()) && room.getRoomType().equals(bookRequest.getRoomType())) {
                    rooms.add(room);
                }
            }
        }
        return getFilteredRooms(null, LocalDate.parse(bookRequest.getStartDate()), LocalDate.parse(bookRequest.getEndDate()), null, rooms, orderForms);
    }

    public List<Room> getBookedRoomsToday(Hotel hotel) {
        List<Room> allRooms = roomRepository.findRoomsByHotel(hotel);
        List<OrderForm> orderForms = orderFormRepository.findAll();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        allRooms.removeAll(getFilteredRooms(null, today, tomorrow, null, allRooms, orderForms));
        return allRooms;
    }

    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }
}
