package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.enums.DeliveryStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void isDelivered() {
        Long orderId = 1L;
        Order order = Order.fake(orderId);

        assertThat(order.isDelivered()).isFalse();

        order.toDelivered();

        assertThat(order.isDelivered()).isTrue();
    }

    @Test
    void changeStatus() {
        Long orderId = 1L;
        Order order = Order.fake(orderId);

        order.toInTransit();

        assertThat(order.getDeliveryStatus()).isEqualTo(DeliveryStatus.IN_TRANSIT.value());

        order.toDelivered();

        assertThat(order.getDeliveryStatus()).isEqualTo(DeliveryStatus.DELIVERED.value());

        order.toShipped();

        assertThat(order.getDeliveryStatus()).isEqualTo(DeliveryStatus.SHIPPED.value());
    }
}
