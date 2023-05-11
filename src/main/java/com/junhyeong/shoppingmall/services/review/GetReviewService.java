package com.junhyeong.shoppingmall.services.review;

import com.junhyeong.shoppingmall.dtos.ReviewDto;
import com.junhyeong.shoppingmall.exceptions.ReviewNotFound;
import com.junhyeong.shoppingmall.models.review.Review;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GetReviewService {
    private final ReviewRepository reviewRepository;

    public GetReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewDto review(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFound::new);

        return review.toDto();
    }
}
