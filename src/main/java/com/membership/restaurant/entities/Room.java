package com.membership.restaurant.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "room", indexes = {@Index(name = "idx_room_type", columnList = "room_type")})
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @ManyToOne
    @JsonProperty("hotel_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    @NotEmpty
    @Column(name = "room_id", nullable = false, unique = true)
    private String roomId;

    @Column(name = "room_price")
    private BigDecimal roomPrice;

    @Column(name = "room_image")
    private String roomImage;

    @Column(name = "booker_num")
    private Integer bookerNum;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "room_state")
    private RoomState roomState;

    @Column(name = "intro", columnDefinition = "TEXT")
    private String intro;

    @Column(name = "room_detail")
    private String roomDetail;

    public Room() {
        this.bookerNum = 0;
        this.roomState = RoomState.IS_AVAILABLE;
    }
}