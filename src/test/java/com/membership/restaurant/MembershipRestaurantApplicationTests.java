package com.membership.restaurant;

import com.membership.restaurant.entities.*;
import com.membership.restaurant.repositories.HotelRepository;
import com.membership.restaurant.repositories.RoomRepository;
import com.membership.restaurant.services.UserService;
import com.membership.restaurant.utils.BCryptHashGenerator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class MembershipRestaurantApplicationTests {
    @Autowired
    UserService userService;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RoomRepository roomRepository;

    private String host = "43.156.9.217";
    //private String host = "127.0.0.1";

    @Test
    void contextLoads() {
    }

    @Test
    void addRootUser() {
        User user = userService.getUserByName("我修院");
        String password = BCryptHashGenerator.generateHash("34a611c6f7934545f1f0c13a61ee2eea");
        if (user == null) {
            user = new User();
        }
        user.setPassword(password);
        user.setTel("+852-11451419");
        user.setAccount("我修院");
        user.setRole(Role.ROOT);
        userService.saveUser(user);
    }

    @Test
    void addAdminUser() {
        User user = userService.getUserByName("田所浩二");
        String password = BCryptHashGenerator.generateHash("34a611c6f7934545f1f0c13a61ee2eea");
        if (user == null) {
            user = new User();
        }
        user.setPassword(password);
        user.setTel("+852-11451419");
        user.setAccount("田所浩二");
        user.setRole(Role.ADMIN);
        userService.saveUser(user);
    }

    @Test
    void addAdminsHotel() {

    }

    @Test
    @Disabled
    void addHotelAndRoom() {
        if (hotelRepository.findHotelByName("淳平酒店") == null) {
            Hotel hotel = new Hotel();
            hotel.setTel("114514");
            hotel.setArea("下北泽");
            hotel.setPicture("http://" + host + ":8080/image/16x9");
            hotel.setIntro("一个一个一个位于下北泽的会员制酒店");
            hotel.setName("淳平酒店");
            hotel.setAddress("下北泽114514");
            hotelRepository.save(hotel);
        }

        if (hotelRepository.findHotelByName("田所酒店") == null) {
            Hotel hotel = new Hotel();
            hotel.setTel("114514-01");
            hotel.setArea("下北泽");
            hotel.setPicture("http://" + host + ":8080/image/16x9");
            hotel.setIntro("一个一个一个顾客会被先辈雷普的酒店");
            hotel.setName("田所酒店");
            hotel.setAddress("下北泽114514-1");
            hotelRepository.save(hotel);
        }

        if (roomRepository.findRoomsByRoomName("雷普房").isEmpty()) {
            for (int i = 114514; i < (114514 + 50); i++) {
                Room room = new Room();
                room.setHotel(hotelRepository.findHotelByName("淳平酒店"));
                room.setRoomDetail("进入雷普房后可以雷普其他人");
                room.setRoomName("雷普房");
                room.setIntro("这是一个一个一个淳平设置的雷普专用房，有各种雷普用具");
                room.setRoomState(RoomState.IS_AVAILABLE);
                room.setRoomId(String.valueOf(i));
                room.setBookerNum(0);
                room.setRoomType("雷普大床房");
                room.setRoomPrice(BigDecimal.valueOf(114514));
                room.setRoomImage("http://" + host + ":8080/image/16x9");
                roomRepository.save(room);
            }
        }

        if (roomRepository.findRoomsByRoomName("昏睡房").isEmpty()) {
            for (int i = 1919; i < (1919 + 60); i++) {
                Room room = new Room();
                room.setHotel(hotelRepository.findHotelByName("田所酒店"));
                room.setRoomDetail("进入昏睡房后可以饮用昏睡红茶");
                room.setRoomName("昏睡房");
                room.setRoomType("昏睡大床房");
                room.setIntro("这是一个一个一个田所浩二设置的昏睡红茶体验房，喝完有奖励，喝不完有惩罚");
                room.setRoomState(RoomState.IS_AVAILABLE);
                room.setRoomId(String.valueOf(i));
                room.setBookerNum(0);
                room.setRoomPrice(BigDecimal.valueOf(1919));
                room.setRoomImage("http://" + host + ":8080/image/16x9");
                roomRepository.save(room);
            }
            for (int i = 810; i < (810 + 60); i++) {
                Room room = new Room();
                room.setHotel(hotelRepository.findHotelByName("田所酒店"));
                room.setRoomDetail("进入昏睡房后可以饮用昏睡红茶");
                room.setRoomName("会员制餐厅房");
                room.setRoomType("餐厅大床房");
                room.setIntro("这是一个一个一个田所浩二设置的餐厅体验房，吃完有奖励，吃不完有惩罚");
                room.setRoomState(RoomState.IS_AVAILABLE);
                room.setRoomId(String.valueOf(i));
                room.setBookerNum(0);
                room.setRoomPrice(BigDecimal.valueOf(810));
                room.setRoomImage("http://" + host + ":8080/image/16x9");
                roomRepository.save(room);
            }
            for (int i = 900; i < (900 + 60); i++) {
                Room room = new Room();
                room.setHotel(hotelRepository.findHotelByName("田所酒店"));
                room.setRoomDetail("进入昏睡房后可以饮用昏睡红茶");
                room.setRoomName("会员制餐厅房");
                room.setRoomType("餐厅小床房");
                room.setIntro("这是一个一个一个田所浩二设置的餐厅体验房，吃完有奖励，吃不完有惩罚");
                room.setRoomState(RoomState.IS_AVAILABLE);
                room.setRoomId(String.valueOf(i));
                room.setBookerNum(0);
                room.setRoomPrice(BigDecimal.valueOf(810));
                room.setRoomImage("http://" + host + ":8080/image/16x9");
                roomRepository.save(room);
            }
        }
    }

}
