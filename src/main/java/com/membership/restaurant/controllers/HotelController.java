package com.membership.restaurant.controllers;

import com.membership.restaurant.dtos.requests.AddHotelRequest;
import com.membership.restaurant.dtos.requests.BookRequest;
import com.membership.restaurant.dtos.requests.EditRoomRequest;
import com.membership.restaurant.dtos.requests.SearchRequest;
import com.membership.restaurant.dtos.responses.*;
import com.membership.restaurant.entities.*;
import com.membership.restaurant.repositories.HotelRepository;
import com.membership.restaurant.repositories.RoomRepository;
import com.membership.restaurant.services.AuthService;
import com.membership.restaurant.services.HotelService;
import com.membership.restaurant.services.OrderFormService;
import com.membership.restaurant.services.RoomService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController
@Log4j2
public class HotelController {
    private final HotelService hotelService;

    private final AuthService authService;

    private final RoomService roomService;
    private final OrderFormService orderFormService;

    @Autowired
    public HotelController(HotelService hotelService, AuthService authService, RoomService roomService, OrderFormService orderFormService,
                           HotelRepository hotelRepository,
                           RoomRepository roomRepository) {
        this.hotelService = hotelService;
        this.authService = authService;
        this.roomService = roomService;
        this.orderFormService = orderFormService;
    }

    @PostMapping(value = "/updateRoom", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map updateRoom(HttpServletResponse response, @RequestBody @Valid EditRoomRequest editRoomRequest) {
        HashMap<String, Object> responseObj = new HashMap();
        HashMap<String, Object> data = new HashMap();
        String session_id = editRoomRequest.getSessionId();

        if (!authService.validate(session_id)) {
            responseObj.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            data.put("message", "401 UNAUTHORIZED - You are not logged-in.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return responseObj;
        }
        User user = authService.getUser(session_id);
        if (!(user.getRole() == Role.ADMIN || (user.getRole() == Role.ROOT))) {
            responseObj.put("code", HttpServletResponse.SC_FORBIDDEN);
            data.put("message", "403 FORBIDDEN - You are not admin nor root user.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return responseObj;
        }

        roomService.editRooms(editRoomRequest);

        responseObj.put("code", HttpServletResponse.SC_OK);
        data.put("message", "Success.");
        responseObj.put("data", data);
        response.setStatus(HttpServletResponse.SC_OK);
        return responseObj;
    }

    @GetMapping(value = "/deleteRoom/{room_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map deleteRoom(HttpServletResponse response, @PathVariable String room_id, @RequestParam @NotNull String session_id) {
        HashMap<String, Object> responseObj = new HashMap();
        HashMap<String, Object> data = new HashMap();
        if (!authService.validate(session_id)) {
            responseObj.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            data.put("message", "401 UNAUTHORIZED - You are not logged-in.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return responseObj;
        }
        User user = authService.getUser(session_id);
        if (!(user.getRole() == Role.ADMIN || (user.getRole() == Role.ROOT))) {
            responseObj.put("code", HttpServletResponse.SC_FORBIDDEN);
            data.put("message", "403 FORBIDDEN - You are not admin nor root user.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return responseObj;
        }

        Room room = roomService.getRoomByRoomId(room_id);

        if (room == null) {
            responseObj.put("code", HttpServletResponse.SC_NOT_FOUND);
            data.put("message", "404 NOT FOUND - The room you requested is not found.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return responseObj;
        }

        roomService.deleteRoom(room_id);

        responseObj.put("code", HttpServletResponse.SC_OK);
        data.put("message", "Success.");
        responseObj.put("data", data);
        response.setStatus(HttpServletResponse.SC_OK);
        return responseObj;
    }

    @PostMapping(value = "/searchHotel", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> searchHotel(HttpServletResponse response, @RequestBody @Valid SearchRequest searchRequest) {
        HashMap responseObj = new HashMap();
        List datum = hotelService.search(searchRequest.getArea(), LocalDate.parse(searchRequest.getStartDate()), LocalDate.parse(searchRequest.getEndDate()), searchRequest.getKeywords());
        responseObj.put("code", HttpServletResponse.SC_OK);
        responseObj.put("data", datum);
        response.setStatus(HttpServletResponse.SC_OK);
        return responseObj;
    }

    @GetMapping(value = "/getAllHotels", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getALlHotels(HttpServletResponse response) {
        HashMap<String, Object> responseObj = new HashMap();
        List datum = hotelService.getHotels();
        responseObj.put("code", HttpServletResponse.SC_OK);
        responseObj.put("data", datum);
        response.setStatus(HttpServletResponse.SC_OK);
        return responseObj;
    }

    @PostMapping(value = "/addHotel", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map addHotel(HttpServletResponse response, @RequestBody @Valid AddHotelRequest addHotelRequest, @RequestParam @NotNull String session_id) {
        HashMap<String, Object> responseObj = new HashMap();
        HashMap<String, Object> data = new HashMap();
        if (!authService.validate(session_id)) {
            responseObj.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            data.put("message", "401 UNAUTHORIZED - You are not logged-in.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return responseObj;
        }
        User user = authService.getUser(session_id);
        if (!(user.getRole() == Role.ROOT)) {
            responseObj.put("code", HttpServletResponse.SC_FORBIDDEN);
            data.put("message", "403 FORBIDDEN - You are not root user.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return responseObj;
        }
        Hotel hotel = new Hotel();
        hotel.setAddress(addHotelRequest.getAddress());
        hotel.setPicture(addHotelRequest.getPicture());
        hotel.setName(addHotelRequest.getName());
        hotel.setArea(addHotelRequest.getArea());
        hotel.setIntro(addHotelRequest.getIntro());
        hotel.setTel(addHotelRequest.getTel());
        hotelService.saveHotel(hotel);

        data.put("message", "Success.");
        responseObj.put("data", data);

        response.setStatus(HttpServletResponse.SC_OK);
        responseObj.put("code", HttpServletResponse.SC_OK);

        return responseObj;
    }

    @GetMapping(value = "/deleteHotel/{hotel_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map deleteHotel(@PathVariable int hotel_id, HttpServletResponse response, @RequestParam @NotNull String session_id) {
        HashMap<String, Object> responseObj = new HashMap();
        HashMap<String, Object> data = new HashMap();
        if (!authService.validate(session_id)) {
            responseObj.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            data.put("message", "401 UNAUTHORIZED - You are not logged-in.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return responseObj;
        }
        User user = authService.getUser(session_id);
        if (!(user.getRole() == Role.ROOT)) {
            responseObj.put("code", HttpServletResponse.SC_FORBIDDEN);
            data.put("message", "403 FORBIDDEN - You are not root user.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return responseObj;
        }

        hotelService.deleteHotel(hotel_id);

        data.put("message", "Success.");
        responseObj.put("data", data);

        response.setStatus(HttpServletResponse.SC_OK);
        responseObj.put("code", HttpServletResponse.SC_OK);

        return responseObj;
    }

    @GetMapping(value = "/hotelDetail", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map getHotelDetail(@RequestParam @NotNull String session_id, HttpServletResponse response) {
        HashMap<String, Object> responseObj = new HashMap();
        HashMap<String, Object> data = new HashMap();
        if (!authService.validate(session_id)) {
            responseObj.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            data.put("message", "401 UNAUTHORIZED - You are not logged-in.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return responseObj;
        }
        User user = authService.getUser(session_id);
        if (!(user.getRole() == Role.ROOT)) {
            responseObj.put("code", HttpServletResponse.SC_FORBIDDEN);
            data.put("message", "403 FORBIDDEN - You are not root user.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return responseObj;
        }

        HotelDetailData hotelDetailData = getHotelsDetailData();
        responseObj.put("code", HttpServletResponse.SC_OK);
        responseObj.put("data", hotelDetailData);
        response.setStatus(HttpServletResponse.SC_OK);
        return responseObj;
    }

    @org.jetbrains.annotations.NotNull
    private HotelDetailData getHotelsDetailData() {
        HotelDetailData hotelDetailData = new HotelDetailData();
        List<HotelDetailElement> hotelDetailElements = new LinkedList<>();
        List<Hotel> hotels = hotelService.getHotels();
        List<OrderForm> orderForms = orderFormService.getAll();

        for (Hotel hotel : hotels) {
            HotelDetailElement hotelDetailElement = new HotelDetailElement();
            List<Room> bookedRooms = hotelService.getBookedRoomsToday(hotel);
            List<Room> rooms = roomService.getRoomsByHotel(hotel);
            int roomCount = 0;
            int consumerCount = 0;
            String intro = hotel.getIntro();
            String picture = hotel.getPicture();
            BigDecimal averagePrice = new BigDecimal(0);
            BigDecimal totalMoney = new BigDecimal(0);

            int i = 0;
            for (Room room : rooms) {
                averagePrice = averagePrice.add(room.getRoomPrice());
                i += 1;
            }
            if (i != 0) {
                averagePrice = averagePrice.divide(new BigDecimal(i), 2, BigDecimal.ROUND_HALF_UP);
            }

            for (Room room : bookedRooms) {
                consumerCount += 1;
                totalMoney = totalMoney.add(room.getRoomPrice());
            }

            for (OrderForm orderForm : orderForms) {
                if (orderForm.getHotel().getName().equals(hotel.getName())) {
                    roomCount += orderForm.getRoomNum();
                }
            }

            hotelDetailElement.setName(hotel.getName());
            hotelDetailElement.setConsumer(consumerCount);
            hotelDetailElement.setIntro(intro);
            hotelDetailElement.setPicture(picture);
            hotelDetailElement.setRoomCount(roomCount);
            hotelDetailElement.setPrice(averagePrice);
            hotelDetailElement.setTotalMoney(totalMoney);
            hotelDetailElement.setId(hotel.getId());
            hotelDetailElement.setArea(hotel.getArea());
            hotelDetailElements.add(hotelDetailElement);
        }
        hotelDetailData.setHotel(hotelDetailElements);
        return hotelDetailData;
    }

    @PostMapping(value = "/cancelRoom/{order_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map cancelRoom(@PathVariable int order_id, HttpServletResponse response, @RequestParam @NotNull String session_id) {
        HashMap<String, Object> responseObj = new HashMap();
        HashMap<String, Object> data = new HashMap();
        if (!authService.validate(session_id)) {
            responseObj.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            data.put("message", "401 UNAUTHORIZED - You are not logged-in.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return responseObj;
        }
        User user = authService.getUser(session_id);
        /*
        if (!(user.getRole() == Role.ADMIN || (user.getRole() == Role.ROOT))) {
            responseObj.put("code", HttpServletResponse.SC_FORBIDDEN);
            data.put("message", "403 FORBIDDEN - You are not admin nor root user.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return responseObj;
        }
         */
        OrderForm orderForm = orderFormService.getOrderFormById(order_id);
        if (orderForm == null) {
            responseObj.put("code", HttpServletResponse.SC_NOT_FOUND);
            data.put("message", "404 NOT FOUND - The order you requested is not found.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return responseObj;
        }

        orderForm.setState(OrderState.IS_DONE);
        orderFormService.saveOrderForm(orderForm);
        data.put("message", "Success.");
        responseObj.put("code", HttpServletResponse.SC_OK);
        responseObj.put("data", data);
        response.setStatus(HttpServletResponse.SC_OK);
        return responseObj;
    }

    @GetMapping(value = "/searchHotel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map getHotel(@PathVariable int id, HttpServletResponse response) {
        HashMap<String, Object> responseObj = new HashMap();
        HashMap<String, Object> data = new HashMap();
        Hotel hotel = hotelService.getHotel(id);
        if (hotel == null) {
            responseObj.put("code", HttpServletResponse.SC_NOT_FOUND);
            data.put("message", "404 Not Found");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return responseObj;
        }
        GetHotelResponse getHotelResponse = new GetHotelResponse();
        getHotelResponse.setTel(hotel.getTel());
        getHotelResponse.setImage(hotel.getPicture());
        getHotelResponse.setName(hotel.getName());
        getHotelResponse.setIntro(hotel.getIntro());
        getHotelResponse.setAddress(hotel.getAddress());
        List<GetHotelRoom> getHotelRooms = new LinkedList<>();
        List<Room> rooms = roomService.getRoomsByHotel(hotel);
        for (Room room : rooms) {
            getHotelRooms.add(new GetHotelRoom(room));
        }
        Set<String> nameSet = new HashSet();
        List<GetHotelRoomName> getHotelRoomNameList = new LinkedList<>();

        for (GetHotelRoom getHotelRoom : getHotelRooms) {
            nameSet.add(getHotelRoom.getRoom_name());
        }

        for (String name : nameSet) {
            GetHotelRoomName getHotelRoomName = new GetHotelRoomName();
            getHotelRoomName.setName(name); //nameList
            getHotelRoomName.initRoomList(getHotelRooms);
            getHotelRoomNameList.add(getHotelRoomName);
        }

        getHotelResponse.setNameList(getHotelRoomNameList);


        responseObj.put("code", HttpServletResponse.SC_OK);
        responseObj.put("data", getHotelResponse);
        response.setStatus(HttpServletResponse.SC_OK);
        return responseObj;
    }

    @PostMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map bookRoom(@RequestBody @Valid BookRequest bookRequest, HttpServletResponse response) {
        HashMap<String, Object> responseObj = new HashMap();
        HashMap<String, Object> data = new HashMap();
        String session_id = bookRequest.getSessionId();
        if (!authService.validate(session_id)) {
            responseObj.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            data.put("message", "401 UNAUTHORIZED - You are not logged-in.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return responseObj;
        }
        if (!(bookRequest.getRoomNum() <= 16 && bookRequest.getRoomNum() > 0)) {
            responseObj.put("code", HttpServletResponse.SC_BAD_REQUEST);
            data.put("message", "400 BAD REQUEST - One user can only book 1-16 room(s) at one time.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return responseObj;
        }
        User user = authService.getUser(session_id);
        user.getAccount();
        List<Room> availRooms = hotelService.getAvailableRoomsForBooking(bookRequest);
        if (availRooms.stream().count() < bookRequest.getRoomNum()) {
            responseObj.put("code", HttpServletResponse.SC_CONFLICT);
            data.put("message", "409 CONFLICT - The roomNum is bigger than the rooms which are available.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return responseObj;
        }
        List<GetHotelRoom> bookedRooms = new LinkedList<>();
        for (int i = 0; i < bookRequest.getRoomNum(); i++) {
            OrderForm orderForm = new OrderForm();
            orderForm.setHotel(hotelService.getHotel(bookRequest.getHotelId()));
            orderForm.setRemarks(bookRequest.getRemark());
            orderForm.setStartDate(LocalDate.parse(bookRequest.getStartDate()));
            orderForm.setEndDate(LocalDate.parse(bookRequest.getEndDate()));
            orderForm.setRoomId(availRooms.get(i));
            orderForm.setRoomType(availRooms.get(i).getRoomType());
            orderForm.setRoomImage(availRooms.get(i).getRoomImage());
            orderForm.setBookerId(bookRequest.getBookerId());
            orderForm.setBookerTel(bookRequest.getBookerTel());
            orderForm.setBookerName(bookRequest.getBookerName());
            orderForm.setState(OrderState.IS_PAID);
            orderForm.setUser(user);
            orderForm.setUserNum(bookRequest.getUserNum());
            BigDecimal price = new BigDecimal(0);
            price = price.add(availRooms.get(i).getRoomPrice());
            orderForm.setPrice(price);
            bookedRooms.add(new GetHotelRoom(availRooms.get(i)));
            orderFormService.saveOrderForm(orderForm);
        }
        data.put("bookedRooms", bookedRooms);
        responseObj.put("data", data);
        responseObj.put("code", HttpServletResponse.SC_OK);
        response.setStatus(HttpServletResponse.SC_OK);
        return responseObj;
    }
}