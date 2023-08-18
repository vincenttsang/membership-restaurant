package com.membership.restaurant.services;

import com.membership.restaurant.entities.OrderForm;
import com.membership.restaurant.entities.User;
import com.membership.restaurant.repositories.FuckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuckService {
    FuckRepository fuckRepository;

    @Autowired
    public FuckService(FuckRepository fuckRepository) {
        this.fuckRepository = fuckRepository;
    }

    public void saveOrderForm(OrderForm orderForm) {
        fuckRepository.save(orderForm);
    }

    public List<OrderForm> getAll() {
        return fuckRepository.findAll();
    }

    public List<OrderForm> findAllByUser(User user) {
        return fuckRepository.findOrderFormsByUser(user);
    }
}
