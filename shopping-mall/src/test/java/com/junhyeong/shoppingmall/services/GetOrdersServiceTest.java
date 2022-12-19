package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.UserName;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetOrdersServiceTest {
    @Test
    void searchOrders() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        GetOrdersService getOrdersService = new GetOrdersService(orderRepository, userRepository);

        UserName userName = new UserName();

        User user = User.fake(userName);

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        Long orderId = 1L;
        List<Order> orders = List.of(
                Order.fake(orderId)
        );

        int page = 1;

        Pageable pageable = PageRequest.of(page -1, 2);

        Page<Order> pageableOrders
                = new PageImpl<>(orders, pageable, orders.size());

        Page<Order> order = getOrdersService.searchOrders(userName, pageable, null, null);

        given(orderRepository.findAll(any(Specification.class), eq(pageable)))
                .willReturn(pageableOrders);

        verify(orderRepository).findAll(any(Specification.class), eq(pageable));
    }
}
