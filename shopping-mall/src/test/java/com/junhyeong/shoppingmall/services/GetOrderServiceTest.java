package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetOrderServiceTest {
    @Test
    void orderDetail() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        GetOrderService getOrderService = new GetOrderService(orderRepository);

        Long orderId = 1L;

        given(orderRepository.findById(orderId)).willReturn(Optional.of(Order.fake(orderId)));
        Order found = getOrderService.orderDetail(orderId);

        assertThat(found).isNotNull();

        verify(orderRepository).findById(orderId);
    }
}
