package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.ReviewDto;
import com.junhyeong.shoppingmall.models.Review;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import com.junhyeong.shoppingmall.services.review.GetReviewService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetReviewServiceTest {
    @Test
    void review() {
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        GetReviewService getReviewService = new GetReviewService(reviewRepository);

        Long reviewId = 1L;

        given(reviewRepository.findById(reviewId))
                .willReturn(Optional.of(Review.fake(reviewId)));

        ReviewDto reviewDto = getReviewService.review(reviewId);

        assertThat(reviewDto).isNotNull();

        verify(reviewRepository).findById(reviewId);
    }
}
