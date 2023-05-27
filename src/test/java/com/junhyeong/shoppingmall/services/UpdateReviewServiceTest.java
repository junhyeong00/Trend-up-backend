package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.review.Review;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import com.junhyeong.shoppingmall.services.review.UpdateReviewService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UpdateReviewServiceTest {
    @Test
    void edit() {
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        UpdateReviewService updateReviewService = new UpdateReviewService(reviewRepository);

        Long reviewId = 1L;

        Review review = Review.fake(reviewId);
        given(reviewRepository.findById(reviewId))
                .willReturn(Optional.of(review));

        updateReviewService.edit(reviewId, 3D, "좋아요", null);

        assertThat(review.rating()).isEqualTo(3D);

        verify(reviewRepository).findById(reviewId);
    }
}
