package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.CreateOrderProductDto;
import com.junhyeong.shoppingmall.dtos.OrderRequest;
import com.junhyeong.shoppingmall.exceptions.OptionNotFound;
import com.junhyeong.shoppingmall.exceptions.ProductNotFound;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.order.Address;
import com.junhyeong.shoppingmall.models.option.Option;
import com.junhyeong.shoppingmall.models.order.Order;
import com.junhyeong.shoppingmall.models.order.PhoneNumber;
import com.junhyeong.shoppingmall.models.product.Product;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.OptionRepository;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.services.order.CreateOrderService;
import com.junhyeong.shoppingmall.utils.KaKaoPay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateOrderServiceTest {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private OptionRepository optionRepository;
    private CreateOrderService createOrderService;
    private KaKaoPay kaKaoPay;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        userRepository = mock(UserRepository.class);
        productRepository = mock(ProductRepository.class);
        optionRepository = mock(OptionRepository.class);
        kaKaoPay = new KaKaoPay();
        createOrderService = new CreateOrderService(
                orderRepository, userRepository, productRepository, optionRepository, kaKaoPay);
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

        OrderRequest orderRequest = OrderRequest.fake();

        String kakao = createOrderService.createOrder(userName, orderRequest);

        assertThat(kakao).isNotBlank();

        verify(userRepository).findByUserName(userName);
        verify(productRepository).findById(productId);
        verify(optionRepository).findById(optionId);
    }

    @Test
    void createOrderWithUserNotFound() {
        UserName userName = new UserName("xxx");

        given(userRepository.findByUserName(userName))
                .willThrow(UserNotFound.class);

        OrderRequest orderRequest = OrderRequest.fake();

       assertThrows(UserNotFound.class,
               () -> createOrderService.createOrder(userName, orderRequest));
    }

    @Test
    void createOrderWithProductNotFound() {
        UserName userName = new UserName("test123");
        Long productId = 999L;

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        given(productRepository.findById(productId))
                .willThrow(ProductNotFound.class);

        OrderRequest orderRequest = OrderRequest.fake();

        assertThrows(ProductNotFound.class,
                () -> createOrderService.createOrder(userName, orderRequest));
    }

    @Test
    void createOrderWithOptionNotFound() {
        UserName userName = new UserName("test123");
        Long productId = 1L;
        Long optionId = 999L;

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        given(productRepository.findById(productId))
                .willReturn(Optional.of(Product.fake(productId)));

        given(optionRepository.findById(optionId))
                .willThrow(OptionNotFound.class);

        OrderRequest orderRequest = OrderRequest.fake();

        assertThrows(OptionNotFound.class,
                () -> createOrderService.createOrder(userName, orderRequest));
    }
}
