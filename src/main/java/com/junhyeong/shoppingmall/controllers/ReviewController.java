package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.DeleteReviewDto;
import com.junhyeong.shoppingmall.dtos.MyReviewsDto;
import com.junhyeong.shoppingmall.dtos.PatchReviewDto;
import com.junhyeong.shoppingmall.dtos.ReviewDto;
import com.junhyeong.shoppingmall.dtos.ReviewRequestDto;
import com.junhyeong.shoppingmall.dtos.ReviewResultDto;
import com.junhyeong.shoppingmall.dtos.ProductReviewsDto;
import com.junhyeong.shoppingmall.exceptions.ReviewWriteFailed;
import com.junhyeong.shoppingmall.models.vo.OrderProduct;
import com.junhyeong.shoppingmall.models.Review;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.services.CreateReviewService;
import com.junhyeong.shoppingmall.services.DeleteReviewsService;
import com.junhyeong.shoppingmall.services.GetReviewService;
import com.junhyeong.shoppingmall.services.GetReviewsService;
import com.junhyeong.shoppingmall.services.PatchReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    private final PatchReviewService patchReviewService;


    public ReviewController(CreateReviewService createReviewService,
                            GetReviewsService getReviewsService,
                            GetReviewService getReviewService,
                            DeleteReviewsService deleteReviewsService, PatchReviewService patchReviewService) {
        this.createReviewService = createReviewService;
        this.getReviewsService = getReviewsService;
        this.getReviewService = getReviewService;
        this.deleteReviewsService = deleteReviewsService;
        this.patchReviewService = patchReviewService;
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

    @DeleteMapping("reviews/{id}")
    public DeleteReviewDto delete(
            @PathVariable("id") Long reviewId
    ) {
        return deleteReviewsService.delete(reviewId);
    }

    @PatchMapping("reviews/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void edit(
            @RequestBody PatchReviewDto patchReviewDto,
            @PathVariable("id") Long reviewId
    ) {
        patchReviewService.edit(
                reviewId,
                patchReviewDto.getRating(),
                patchReviewDto.getContent(),
                patchReviewDto.getImageUrl());
    }

    @ExceptionHandler(ReviewWriteFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String reviewWriteFail() {
        return "write fail";
    }
}
