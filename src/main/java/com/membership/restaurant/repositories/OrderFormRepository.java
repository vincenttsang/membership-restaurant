package com.membership.restaurant.repositories;

import com.membership.restaurant.entities.Hotel;
import com.membership.restaurant.entities.OrderForm;
import com.membership.restaurant.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface OrderFormRepository extends JpaRepository<OrderForm, Integer> {
    List<OrderForm> findOrderFormsByUser(User user);

    OrderForm findOrderFormById(int id);

    @Transactional
    @Modifying
    void deleteOrderFormsByHotel(Hotel hotel);

    @Transactional
    @Modifying
    void deleteOrderFormsByRoomId(String roomId);
}