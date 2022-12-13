package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.OrderProductDto;
import com.junhyeong.shoppingmall.models.Address;
import com.junhyeong.shoppingmall.models.Option;
import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.UserName;
import com.junhyeong.shoppingmall.repositories.OptionRepository;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CreateOrderServiceTest {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private OptionRepository optionRepository;
    private CreateOrderService createOrderService;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        userRepository = mock(UserRepository.class);
        productRepository = mock(ProductRepository.class);
        optionRepository = mock(OptionRepository.class);
        createOrderService = new CreateOrderService(
                orderRepository, userRepository, productRepository, optionRepository);
    }

    @Test
    void createOrder() {
        UserName userName = new UserName("test123");
        Long productId = 1L;
        Long optionId = 1L;
        Long orderId = 1L;

        Order order = Order.fake(orderId);

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        given(productRepository.findById(productId))
                .willReturn(Optional.of(Product.fake(productId)));

        given(optionRepository.findById(optionId))
                .willReturn(Optional.of(Option.fake(optionId)));

        given(orderRepository.save(order))
                .willReturn(order);

        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        orderProductDtos.add(new OrderProductDto(productId, optionId, 2L));

        Address address = new Address(
                order.address().zipCode(),
                order.address().roadAddress(),
                order.address().detailAddress());

        Order found = createOrderService.createOrder(
                userName, "01012341234", "배준형", order.payment()
                , order.totalPrice(), order.deliveryFee(), orderProductDtos,
                order.deliveryRequest(), address);

        assertThat(found).isNotNull();

        verify(userRepository).findByUserName(userName);
        verify(productRepository).findById(productId);
        verify(optionRepository, times(2)).findById(optionId);
        verify(orderRepository).save(found);
    }
}
