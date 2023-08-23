package com.membership.restaurant.repositories;

import com.membership.restaurant.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    boolean existsNameByName(Object unknownAttr1);

    List<Hotel> findAll();

    Hotel findHotelByName(String name);

    @Transactional
    @Modifying
    void deleteHotelById(int id);

    Hotel findHotelById(Integer id);
}