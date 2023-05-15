package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.CreateReviewRequest;
import com.junhyeong.shoppingmall.dtos.DeleteReviewDto;
import com.junhyeong.shoppingmall.dtos.MyReviewsDto;
import com.junhyeong.shoppingmall.dtos.ProductReviewsDto;
import com.junhyeong.shoppingmall.dtos.ReviewDto;
import com.junhyeong.shoppingmall.dtos.ReviewRequestDto;
import com.junhyeong.shoppingmall.dtos.ReviewResultDto;
import com.junhyeong.shoppingmall.dtos.UpdateReviewDto;
import com.junhyeong.shoppingmall.exceptions.OrderFailed;
import com.junhyeong.shoppingmall.exceptions.OrderNotFound;
import com.junhyeong.shoppingmall.exceptions.ReviewWriteFailed;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.order.OrderProduct;
import com.junhyeong.shoppingmall.models.review.Review;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.services.review.CreateReviewService;
import com.junhyeong.shoppingmall.services.review.DeleteReviewsService;
import com.junhyeong.shoppingmall.services.review.GetReviewService;
import com.junhyeong.shoppingmall.services.review.GetReviewsService;
import com.junhyeong.shoppingmall.services.review.UpdateReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final GetReviewService getReviewService;
    private final DeleteReviewsService deleteReviewsService;
    private final UpdateReviewService updateReviewService;

    public ReviewController(CreateReviewService createReviewService,
                            GetReviewsService getReviewsService,
                            GetReviewService getReviewService,
                            DeleteReviewsService deleteReviewsService, UpdateReviewService updateReviewService) {
        this.createReviewService = createReviewService;
        this.getReviewsService = getReviewsService;
        this.getReviewService = getReviewService;
        this.deleteReviewsService = deleteReviewsService;
        this.updateReviewService = updateReviewService;
    }

    @GetMapping("products/{productId}/reviews")
    public ProductReviewsDto reviews(
            @PathVariable("productId") Long productId,
            @PageableDefault(size = 8, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Review> reviews = getReviewsService.reviews(productId, pageable);

        int totalPageCount = reviews.getTotalPages();

        Long totalReviewCount = reviews.getTotalElements();

        double totalRating = getReviewsService.totalRating(productId, totalReviewCount);

        List<ReviewDto> reviewDtos = getReviewsService.toDto(reviews);

        return new ProductReviewsDto(reviewDtos, totalPageCount, totalReviewCount, totalRating);
    }

    @GetMapping("reviews")
    public MyReviewsDto myReviews(
            @RequestAttribute("userName") UserName userName,
            @PageableDefault(size = 8, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return getReviewsService.myReviews(userName, pageable);
    }

    @GetMapping("reviews/{id}")
    public ReviewDto review(
            @PathVariable("id") Long reviewId
    ) {
        return getReviewService.review(reviewId);
    }

    @PostMapping("reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResultDto write(
            @RequestAttribute("userName") UserName userName,
            @Validated @RequestBody ReviewRequestDto reviewRequestDto
    ) {
            CreateReviewRequest createReviewRequest = CreateReviewRequest.of(reviewRequestDto);

            Review review = createReviewService.write(userName, createReviewRequest);

            return review.toResultDto();
    }

    @DeleteMapping("reviews/{id}")
    public DeleteReviewDto delete(
            @PathVariable("id") Long reviewId
    ) {
        return deleteReviewsService.delete(reviewId);
    }

    @PatchMapping("reviews/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void edit(
            @Validated @RequestBody UpdateReviewDto updateReviewDto,
            @PathVariable("id") Long reviewId
    ) {
        updateReviewService.edit(
                reviewId,
                updateReviewDto.getRating(),
                updateReviewDto.getContent(),
                updateReviewDto.getImageUrl());
    }

    @ExceptionHandler(ReviewWriteFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String reviewWriteFail(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(UserNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFound(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(OrderNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String orderNotFound(Exception e) {
        return e.getMessage();
    }
}
