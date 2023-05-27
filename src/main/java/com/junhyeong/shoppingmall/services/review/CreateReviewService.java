package com.junhyeong.shoppingmall.services.review;

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
import com.junhyeong.shoppingmall.specifications.ReviewSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CreateReviewService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    public CreateReviewService(UserRepository userRepository,
                               OrderRepository orderRepository,
                               ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public Review write(UserName userName, CreateReviewRequest createReviewRequest) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        Order order = orderRepository.findById(createReviewRequest.getOrderId())
                .orElseThrow(OrderNotFound::new);

        checkDelivered(order);

        Specification<Review> spec = getReviewSpecification(createReviewRequest, user, order);

        checkReviewExists(spec);

        Review review = new Review(
                user.id(), order.id(),
                createReviewRequest.getOrderProduct(),
                createReviewRequest.getRating(),
                createReviewRequest.getContent(),
                createReviewRequest.getImageUrl());

        reviewRepository.save(review);
        return review;
    }

    private void checkDelivered(Order order) {
        if (!order.isDelivered()) {
            throw new ReviewWriteFailed("배송완료된 상품만 리뷰를 작성할 수 있습니다");
        }
    }

    private Specification<Review> getReviewSpecification(CreateReviewRequest createReviewRequest, User user, Order order) {
        Specification<Review> spec = Specification.where(ReviewSpecification.equalUserId(user.id()));
        spec = spec.and(ReviewSpecification.equalOrderId(order.id()));
        spec = spec.and(ReviewSpecification.equalProductId(createReviewRequest.getOrderProduct().productId()));
        spec = spec.and(ReviewSpecification.equalOptionId(createReviewRequest.getOrderProduct().optionId()));
        return spec;
    }

    private void checkReviewExists(Specification<Review> spec) {
        if (reviewRepository.exists(spec)) {
            Optional<Review> review = reviewRepository.findOne(spec);

            if (review.get().isDeleted()) {
                throw new ReviewWriteFailed("삭제한 리뷰는 재등록이 불가능합니다");
            }

            throw new ReviewWriteFailed("이미 작성한 리뷰입니다");
        }
    }
}
