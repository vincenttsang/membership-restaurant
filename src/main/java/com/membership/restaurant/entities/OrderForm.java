package com.membership.restaurant.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "order_form")
public class OrderForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JsonProperty("hotel_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    @ManyToOne
    @JsonProperty("room_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roomId")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    private Room roomId;

    @JsonProperty("room_type")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "room_type")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "room_type", referencedColumnName = "room_type")
    private String roomType;

    @ManyToOne
    @JsonProperty("user_account")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "account")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(name = "user_account", referencedColumnName = "account")
    private User user;

    @NotEmpty
    @Column(name = "booker_id_card", nullable = false)
    private String bookerId;

    @NotNull
    @Column(name = "booker_name", nullable = false)
    private String bookerName;

    @Column(name = "booker_tel", nullable = false)
    private String bookerTel;

    @NotNull
    @Column(name = "state", nullable = false)
    private OrderState state;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "time")
    private String time;

    @Column(name = "start_date")
    @JsonProperty("start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    @JsonProperty("end_date")
    private LocalDate endDate;

    @Column(name = "user_num")
    @JsonProperty("user_num")
    private int userNum;

    @Column(name = "room_num")
    @JsonProperty("room_num")
    private int roomNum;
}