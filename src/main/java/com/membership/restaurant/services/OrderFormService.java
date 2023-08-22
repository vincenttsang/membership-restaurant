package com.membership.restaurant.services;

import com.membership.restaurant.entities.Hotel;
import com.membership.restaurant.entities.OrderForm;
import com.membership.restaurant.entities.User;
import com.membership.restaurant.repositories.OrderFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderFormService {
    OrderFormRepository orderFormRepository;

    @Autowired
    public OrderFormService(OrderFormRepository orderFormRepository) {
        this.orderFormRepository = orderFormRepository;
    }

    public void saveOrderForm(OrderForm orderForm) {
        orderFormRepository.save(orderForm);
    }

    public List<OrderForm> getAll() {
        return orderFormRepository.findAll();
    }

    public List<OrderForm> findAllByUser(User user) {
        return orderFormRepository.findOrderFormsByUser(user);
    }

    public OrderForm getOrderFormById(int id) {
        return orderFormRepository.findOrderFormById(id);
    }

    public List findAllByHotel(Hotel hotel) {
        return orderFormRepository.findOrderFormsByHotel(hotel);
    }
}
