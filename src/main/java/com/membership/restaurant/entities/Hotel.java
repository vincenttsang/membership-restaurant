package com.membership.restaurant.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "area")
    private String area;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "intro", columnDefinition = "TEXT")
    private String intro;

    @Column(name = "picture")
    private String picture;

    @Column(name = "tel")
    private String tel;

    @Column(name = "address")
    private String address;
}