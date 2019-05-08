package com.iptech.service;

import com.iptech.domain.Orders;
import com.iptech.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrdersService {
    private OrdersRepository ordersRepo;
    public OrdersService(OrdersRepository ordersRepo) { this.ordersRepo = ordersRepo;}
    public Iterable<Orders> list() {
        return ordersRepo.findAll();
    }
    public Optional<Orders> findOne(long id) { return ordersRepo.findById(id);}
    public Orders save(Orders orders) {
        return ordersRepo.save(orders);
    }
}
