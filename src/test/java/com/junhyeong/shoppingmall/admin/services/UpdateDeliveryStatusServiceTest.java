package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UpdateDeliveryStatusServiceTest {
    @Test
    void changeDeliveryStatus() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        UpdateDeliveryStatusService updateDeliveryStatusService = new UpdateDeliveryStatusService(orderRepository);

        Long orderId = 1L;

        Order order = Order.fake(orderId);

        given(orderRepository.findById(orderId)).willReturn(Optional.of(order));
        updateDeliveryStatusService.changeDeliveryStatus(orderId, "배송중");

        assertThat(order.deliveryStatus()).isEqualTo("배송중");

        verify(orderRepository).findById(orderId);
    }
}
