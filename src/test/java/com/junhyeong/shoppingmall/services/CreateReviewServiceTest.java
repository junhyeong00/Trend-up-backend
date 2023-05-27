package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.CreateReviewRequest;
import com.junhyeong.shoppingmall.exceptions.OrderNotFound;
import com.junhyeong.shoppingmall.exceptions.ReviewWriteFailed;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.order.Order;
import com.junhyeong.shoppingmall.models.order.OrderProduct;
import com.junhyeong.shoppingmall.models.review.Review;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.services.review.CreateReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateReviewServiceTest {
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private ReviewRepository reviewRepository;
    private CreateReviewService createReviewService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        orderRepository = mock(OrderRepository.class);
        reviewRepository = mock(ReviewRepository.class);
        createReviewService = new CreateReviewService(
                userRepository,
                orderRepository, reviewRepository);
    }

    @Test
    void write() {
        UserName userName = new UserName("test123");
        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        Long orderId = 1L;
        Order order = Order.fake(orderId);
        order.toDelivered();

        given(orderRepository.findById(orderId))
                .willReturn(Optional.of(order));

        Long reviewId = 1L;
        given(reviewRepository.save(Review.fake(reviewId)))
                .willReturn(Review.fake(reviewId));

        CreateReviewRequest createReviewRequest = CreateReviewRequest.fake(orderId);

        Review review = createReviewService.write(userName, createReviewRequest);

        assertThat(review).isNotNull();

        verify(userRepository).findByUserName(userName);
        verify(orderRepository).findById(orderId);
        verify(reviewRepository).save(review);
    }

    @Test
    void writeFailWithShippedOrder() {
        UserName userName = new UserName("test123");
        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        Long orderId = 1L;
        Order order = Order.fake(orderId);

        given(orderRepository.findById(orderId))
                .willReturn(Optional.of(order));

        CreateReviewRequest createReviewRequest = CreateReviewRequest.fake(orderId);

        assertThrows(ReviewWriteFailed.class, () -> {
            createReviewService.write(userName, createReviewRequest);
        });
    }

    @Test
    void writeFailWithExistReview() {
        UserName userName = new UserName("test123");
        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        Long orderId = 1L;
        Order order = Order.fake(orderId);
        order.toDelivered();

        given(orderRepository.findById(orderId))
                .willReturn(Optional.of(order));

        CreateReviewRequest createReviewRequest = CreateReviewRequest.fake(orderId);

        given(reviewRepository.exists(any(Specification.class)))
                .willThrow(new ReviewWriteFailed("이미 작성한 리뷰입니다"));

        assertThrows(ReviewWriteFailed.class, () -> {
            createReviewService.write(userName, createReviewRequest);
        });
    }

    @Test
    void writeFailWithUserNotFound() {
        UserName userName = new UserName("xxx");
        given(userRepository.findByUserName(userName)).willThrow(UserNotFound.class);

        Long orderId = 1L;
        Order order = Order.fake(orderId);
        order.toDelivered();

        CreateReviewRequest createReviewRequest = CreateReviewRequest.fake(orderId);

        assertThrows(UserNotFound.class, () -> {
            createReviewService.write(userName, createReviewRequest);
        });
    }

    @Test
    void writeFailWithOrderNotFound() {
        UserName userName = new UserName("test123");
        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        Long orderId = 999L;
        Order order = Order.fake(orderId);
        order.toDelivered();

        given(orderRepository.findById(orderId)).willThrow(OrderNotFound.class);

        CreateReviewRequest createReviewRequest = CreateReviewRequest.fake(orderId);

        assertThrows(OrderNotFound.class, () -> {
            createReviewService.write(userName, createReviewRequest);
        });
    }
}
