package com.membership.restaurant.repositories;

import com.membership.restaurant.entities.OrderForm;
import com.membership.restaurant.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface FuckRepository extends JpaRepository<OrderForm, Integer> {
    List<OrderForm> findOrderFormsByUser(User user);
}