package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.dtos.OrderDto;
import com.junhyeong.shoppingmall.dtos.OrderProductDto;
import com.junhyeong.shoppingmall.models.order.Order;
import com.junhyeong.shoppingmall.models.order.OrderProduct;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GetOrdersAdminService {
    private final OrderRepository orderRepository;

    public GetOrdersAdminService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<Order> orders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public List<OrderDto> toDto(Page<Order> orders) {
        return orders.stream().map(order -> {
            List<OrderProductDto> orderProductDtos = order.orderProducts().stream()
                    .map(OrderProduct::toOrderProductDto).toList();

            return order.toDto(orderProductDtos);
        }).toList();
    }
}
