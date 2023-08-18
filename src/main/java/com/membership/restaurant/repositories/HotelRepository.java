package com.membership.restaurant.repositories;

import com.membership.restaurant.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    boolean existsNameByName(Object unknownAttr1);

    List<Hotel> findAll();

    Hotel findHotelByName(String name);
}