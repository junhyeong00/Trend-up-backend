package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.ReviewDto;
import com.junhyeong.shoppingmall.dtos.ReviewRequestDto;
import com.junhyeong.shoppingmall.dtos.ReviewResultDto;
import com.junhyeong.shoppingmall.dtos.ReviewsDto;
import com.junhyeong.shoppingmall.exceptions.ReviewWriteFailed;
import com.junhyeong.shoppingmall.models.OrderProduct;
import com.junhyeong.shoppingmall.models.Review;
import com.junhyeong.shoppingmall.models.UserName;
import com.junhyeong.shoppingmall.services.CreateReviewService;
import com.junhyeong.shoppingmall.services.GetReviewsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {
    private final CreateReviewService createReviewService;
    private final GetReviewsService getReviewsService;

    public ReviewController(CreateReviewService createReviewService, GetReviewsService getReviewsService) {
        this.createReviewService = createReviewService;
        this.getReviewsService = getReviewsService;
    }

    @GetMapping("products/{productId}/reviews")
    public ReviewsDto reviews(
            @PathVariable("productId") Long productId,
            @PageableDefault(size = 8) Pageable pageable
            ) {
        Page<Review> reviews = getReviewsService.reviews(productId, pageable);

        int totalPageCount = reviews.getTotalPages();

        Long totalReviewCount = reviews.getTotalElements();

        double totalRating = getReviewsService.totalRating(productId, totalReviewCount);

        List<ReviewDto> reviewDtos = getReviewsService.toDto(reviews);

        return new ReviewsDto(reviewDtos, totalPageCount, totalReviewCount, totalRating);
    }

    @PostMapping("review")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResultDto write(
            @RequestAttribute("userName") UserName userName,
            @RequestBody ReviewRequestDto reviewRequestDto
    ) {
        OrderProduct orderProduct = new OrderProduct(
                reviewRequestDto.getProductId(),
                reviewRequestDto.getProductName(),
                reviewRequestDto.getProductPrice(),
                reviewRequestDto.getOptionId(),
                reviewRequestDto.getProductOption(),
                reviewRequestDto.getProductQuantity(),
                reviewRequestDto.getProductImage()
        );

        Review review = createReviewService.write(
                userName,
                reviewRequestDto.getRating(),
                reviewRequestDto.getContent(),
                reviewRequestDto.getOrderId(),
                reviewRequestDto.getImageUrl(),
                orderProduct
        );
        return review.toResultDto();
    }

    @ExceptionHandler(ReviewWriteFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String reviewWriteFail() {
        return "write fail";
    }
}
