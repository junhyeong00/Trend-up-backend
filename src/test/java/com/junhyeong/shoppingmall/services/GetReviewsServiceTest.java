package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.OrderProduct;
import com.junhyeong.shoppingmall.models.Review;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetReviewsServiceTest {
    @Test
    void reviews() {
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        GetReviewsService getReviewsService = new GetReviewsService(reviewRepository, userRepository);

        OrderProduct orderProduct = OrderProduct.fake(1L, 1L);

        List<Review> reviews = List.of(
                new Review(1L, 1L, 1L, orderProduct, 5D, "부드럽고 따뜻해요"),
                new Review(2L, 1L, 1L, orderProduct, 5D, "부드럽고 따뜻해요"),
                new Review(3L, 1L, 1L, orderProduct, 5D, "부드럽고 따뜻해요")
        );

        int page = 1;

        Pageable pageable = PageRequest.of(page - 1, 4);

        Page<Review> pageableReview = new PageImpl<>(reviews, pageable, reviews.size());

        Long productId = 1L;

        given(reviewRepository.findAll(any(Specification.class), eq(pageable)))
                .willReturn(pageableReview);

        Page<Review> found = getReviewsService.reviews(productId, pageable);

        assertThat(found).hasSize(3);

        verify(reviewRepository).findAll(any(Specification.class), eq(pageable));
    }
}
