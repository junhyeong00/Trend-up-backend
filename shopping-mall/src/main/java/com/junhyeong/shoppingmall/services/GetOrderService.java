package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.exceptions.OrderNotFound;
import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GetOrderService {
    private final OrderRepository orderRepository;

    public GetOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order orderDetail(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFound::new);
    }
}
