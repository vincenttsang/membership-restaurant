package com.membership.restaurant.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "admin_hotel_link")
public class AdminHotelLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonProperty("user_id")
    @Column(name = "user_id", unique = true)
    private Integer userId;

    @JsonProperty("hotel_id")
    @Column(name = "hotel_id")
    private Integer hotelId;
}