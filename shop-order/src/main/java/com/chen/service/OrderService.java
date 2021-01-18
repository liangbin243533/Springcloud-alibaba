package com.chen.service;

import com.chen.domain.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    void createOrder(Order order);
}
