package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.DeleteReviewDto;
import com.junhyeong.shoppingmall.models.review.Review;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import com.junhyeong.shoppingmall.services.review.DeleteReviewsService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DeleteReviewsServiceTest {
    @Test
    void delete() {
        ReviewRepository reviewRepository = mock(ReviewRepository.class);
        DeleteReviewsService deleteReviewsService = new DeleteReviewsService(reviewRepository);

        Long reviewId = 1L;
        given(reviewRepository.findById(reviewId))
                .willReturn(Optional.of(Review.fake(reviewId)));

        DeleteReviewDto deleteReviewDto = deleteReviewsService.delete(reviewId);

        assertThat(deleteReviewDto).isNotNull();

        verify(reviewRepository).findById(reviewId);
    }
}
