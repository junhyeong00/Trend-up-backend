package com.junhyeong.shoppingmall.services.review;

import com.junhyeong.shoppingmall.exceptions.ReviewNotFound;
import com.junhyeong.shoppingmall.models.Review;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UpdateReviewService {
    private final ReviewRepository reviewRepository;

    public UpdateReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void edit(Long reviewId, Double rating, String content, String imageUrl) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFound::new);

        review.update(rating, content, imageUrl);
    }
}
