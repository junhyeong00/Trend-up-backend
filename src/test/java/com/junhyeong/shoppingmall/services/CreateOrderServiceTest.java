package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.CreateOrderProductDto;
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

        List<CreateOrderProductDto> orderProductDtos = new ArrayList<>();
        orderProductDtos.add(new CreateOrderProductDto(productId, optionId, 2L));

        Address address = new Address(
                order.address().zipCode(),
                order.address().roadAddress(),
                order.address().detailAddress());

        String kakao = createOrderService.createOrder(
                userName, new PhoneNumber("01012341234"), "배준형", order.payment()
                , order.totalPrice(), order.deliveryFee(), orderProductDtos,
                order.deliveryRequest(), address);

        assertThat(kakao).isNotBlank();

        verify(userRepository).findByUserName(userName);
        verify(productRepository).findById(productId);
        verify(optionRepository).findById(optionId);
    }
}
