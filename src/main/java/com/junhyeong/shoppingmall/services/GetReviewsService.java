package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.ReviewDto;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.Review;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.specifications.ReviewSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GetReviewsService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public GetReviewsService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public Page<Review> reviews(Long productId, Pageable pageable) {
        Specification<Review> spec = Specification.where(ReviewSpecification.equalProductId(productId));
        return reviewRepository.findAll(spec ,pageable);
    }

    public List<ReviewDto> toDto(Page<Review> reviews) {
        return reviews.stream().map(review -> {
            User user = userRepository.findById(review.userId())
                    .orElseThrow(UserNotFound::new);
            return review.toDto(user.userName());
        }).toList();
    }

    public double totalRating(Long productId, Long totalReviewCount) {
        Specification<Review> spec = Specification.where(ReviewSpecification.equalProductId(productId));
        List<Review> reviews = reviewRepository.findAll(spec);

        return reviews.stream().
                mapToDouble(review -> review.rating()).
                reduce(0, (acc, rating) -> acc + rating) / totalReviewCount;
    }
}
