package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.DeleteReviewDto;
import com.junhyeong.shoppingmall.exceptions.ReviewNotFound;
import com.junhyeong.shoppingmall.models.Review;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DeleteReviewsService {
    private final ReviewRepository reviewRepository;

    public DeleteReviewsService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public DeleteReviewDto delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFound::new);

        review.delete();
        return review.toDeleteDto();
    }
}
