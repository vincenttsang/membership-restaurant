package com.membership.restaurant.services;

import com.membership.restaurant.entities.AdminHotelLink;
import com.membership.restaurant.entities.Hotel;
import com.membership.restaurant.entities.User;
import com.membership.restaurant.repositories.AdminHotelLinkRepository;
import com.membership.restaurant.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminHotelLinkService {
    private final AdminHotelLinkRepository adminHotelLinkRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public AdminHotelLinkService(AdminHotelLinkRepository adminHotelLinkRepository, HotelRepository hotelRepository) {
        this.adminHotelLinkRepository = adminHotelLinkRepository;
        this.hotelRepository = hotelRepository;
    }

    public Hotel getAdminsHotel(User user) {
        AdminHotelLink adminHotelLink = adminHotelLinkRepository.getAdminHotelLinkByUserId(user.getId());
        return hotelRepository.findHotelById(adminHotelLink.getHotelId());
    }

    public void setAdminsHotel(User admin, Hotel hotel) {
        AdminHotelLink adminHotelLink = adminHotelLinkRepository.getAdminHotelLinkByUserId(admin.getId());
        if (adminHotelLink == null) {
            adminHotelLink = new AdminHotelLink();
        }
        adminHotelLink.setHotelId(hotel.getId());
        adminHotelLinkRepository.save(adminHotelLink);
    }
}
