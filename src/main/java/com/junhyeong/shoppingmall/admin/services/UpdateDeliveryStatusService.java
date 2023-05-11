package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.exceptions.OrderNotFound;
import com.junhyeong.shoppingmall.models.order.Order;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UpdateDeliveryStatusService {
    private final OrderRepository orderRepository;

    public UpdateDeliveryStatusService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void changeDeliveryStatus(Long orderId, String deliveryStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFound::new);

        switch (deliveryStatus) {
            case "배송완료" -> order.toDelivered();
            case "배송중" -> order.toInTransit();
            case "배송 준비중" -> order.toShipped();
        }
    }
}