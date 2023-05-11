package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.order.Order;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import com.junhyeong.shoppingmall.services.order.GetOrderService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetOrderServiceTest {
    @Test
    void orderDetail() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        GetOrderService getOrderService = new GetOrderService(orderRepository, reviewRepository);

        Long orderId = 1L;

        given(orderRepository.findById(orderId)).willReturn(Optional.of(Order.fake(orderId)));
        Order found = getOrderService.orderDetail(orderId);

        assertThat(found).isNotNull();

        verify(orderRepository).findById(orderId);
    }
}
