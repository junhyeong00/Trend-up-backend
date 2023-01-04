package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetOrdersServiceTest {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;
    private GetOrdersService getOrdersService;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        userRepository = mock(UserRepository.class);
        reviewRepository = mock(ReviewRepository.class);
        getOrdersService = new GetOrdersService(orderRepository, userRepository, reviewRepository);
    }

    @Test
    void searchOrders() {
        UserName userName = new UserName();

        User user = User.fake(userName);

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        Long orderId = 1L;
        List<Order> orders = List.of(
                Order.fake(orderId)
        );

        int page = 1;

        Pageable pageable = PageRequest.of(page - 1, 2);

        Page<Order> pageableOrders
                = new PageImpl<>(orders, pageable, orders.size());

        given(orderRepository.findAll(any(Specification.class), eq(pageable)))
                .willReturn(pageableOrders);

        Page<Order> found = getOrdersService.searchOrders(userName, pageable, null, null);

        assertThat(found).hasSize(1);

        verify(orderRepository).findAll(any(Specification.class), eq(pageable));
    }

//    @Test
//    void searchOrdersWithDateTime() {
//        UserName userName = new UserName();
//
//        User user = User.fake(userName);
//
//        given(userRepository.findByUserName(userName))
//                .willReturn(Optional.of(user));
//
//        Address address = new Address(123L, "인천", "102호");
//        List<OrderProduct> orderProducts = new ArrayList<>();
//        OrderProduct orderProduct = OrderProduct.fake(1L, 1L);
//        PhoneNumber phoneNumber = new PhoneNumber("01012341234");
//        orderProducts.add(orderProduct);
//
//        Long orderId = 1L;
//        List<Order> orders = List.of(
//                new Order(orderId, 1L, phoneNumber, "배준형", 23000L, 20000L, 3000L, "배송완료",
//                        "", orderProducts, address, LocalDateTime.of(2022, 12, 5, 0, 0)),
//                new Order(2L, 1L, phoneNumber, "배준형", 23000L, 20000L, 3000L, "배송완료",
//                        "", orderProducts, address, LocalDateTime.of(2022, 12, 9, 0, 0)),
//                new Order(3L, 1L, phoneNumber, "배준형", 23000L, 20000L, 3000L, "배송완료",
//                        "", orderProducts, address, LocalDateTime.of(2022, 12, 9, 0, 0))
//        );
//
//        int page = 1;
//
//        Pageable pageable = PageRequest.of(page - 1, 4);
//
//        Page<Order> pageableOrders
//                = new PageImpl<>(orders, pageable, orders.size());
//
//        Specification<Order> spec = Specification.where(OrderSpecification.equalUserId(user.id()));
//
//        spec = spec.and(OrderSpecification.betweenCreatedDatetime(
//                LocalDateTime.of(2022, 12, 7, 0, 0), LocalDateTime.of(2022, 12, 10, 0, 0)));
//
//        given(orderRepository.findAll(spec, pageable))
//                .willReturn(pageableOrders);
//
//        Page<Order> found = getOrdersService.searchOrders(
//                userName, pageable, LocalDateTime.of(2022, 12, 7, 0, 0), LocalDateTime.of(2022, 12, 10, 0, 0));
//
//        assertThat(found).hasSize(2);
//
//        verify(orderRepository).findAll(spec, pageable);
//    }
}
