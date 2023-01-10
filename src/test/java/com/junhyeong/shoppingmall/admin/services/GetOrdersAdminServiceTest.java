package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetOrdersAdminServiceTest {
    private OrderRepository orderRepository;
    private GetOrdersAdminService getOrdersAdminService;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        getOrdersAdminService = new GetOrdersAdminService(orderRepository);
    }

    @Test
    void orders() {
        Long orderId = 1L;
        List<Order> orders = List.of(
                Order.fake(orderId)
        );

        int page = 1;

        Pageable pageable = PageRequest.of(page - 1, 2);

        Page<Order> pageableOrders
                = new PageImpl<>(orders, pageable, orders.size());

        given(orderRepository.findAll(pageable))
                .willReturn(pageableOrders);

        Page<Order> found = getOrdersAdminService.orders(pageable);

        assertThat(found).hasSize(1);

        verify(orderRepository).findAll(pageable);
    }
}
