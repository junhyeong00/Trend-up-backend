package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.ReviewRequestDto;
import com.junhyeong.shoppingmall.dtos.ReviewResultDto;
import com.junhyeong.shoppingmall.exceptions.OrderFailed;
import com.junhyeong.shoppingmall.exceptions.ReviewWriteFailed;
import com.junhyeong.shoppingmall.models.OrderProduct;
import com.junhyeong.shoppingmall.models.Review;
import com.junhyeong.shoppingmall.models.UserName;
import com.junhyeong.shoppingmall.services.CreateReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    private final CreateReviewService createReviewService;

    public ReviewController(CreateReviewService createReviewService) {
        this.createReviewService = createReviewService;
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
