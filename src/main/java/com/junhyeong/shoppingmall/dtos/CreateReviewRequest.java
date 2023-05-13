package com.junhyeong.shoppingmall.dtos;

import com.junhyeong.shoppingmall.models.order.OrderProduct;

public class CreateReviewRequest {
    private OrderProduct orderProduct;
    private Double rating;
    private String content;
    private Long orderId;
    private String imageUrl;

    public CreateReviewRequest(OrderProduct orderProduct, Double rating, String content, Long orderId, String imageUrl) {
        this.orderProduct = orderProduct;
        this.rating = rating;
        this.content = content;
        this.orderId = orderId;
        this.imageUrl = imageUrl;
    }

    public static CreateReviewRequest fake(Long orderId) {
        OrderProduct orderProduct = OrderProduct.fake(1L, 1L);
        return new CreateReviewRequest(
                orderProduct, 5D, "부드럽고 따뜻해요", orderId, "이미지");
    }

    public static CreateReviewRequest of(ReviewRequestDto reviewRequestDto) {
        return new CreateReviewRequest(
                new OrderProduct(
                        reviewRequestDto.getProductId(),
                        reviewRequestDto.getProductName(),
                        reviewRequestDto.getProductPrice(),
                        reviewRequestDto.getOptionId(),
                        reviewRequestDto.getProductOption(),
                        reviewRequestDto.getProductQuantity(),
                        reviewRequestDto.getProductImage()
                ),
                reviewRequestDto.getRating(),
                reviewRequestDto.getContent(),
                reviewRequestDto.getOrderId(),
                reviewRequestDto.getImageUrl()
        );
    }

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public Double getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
