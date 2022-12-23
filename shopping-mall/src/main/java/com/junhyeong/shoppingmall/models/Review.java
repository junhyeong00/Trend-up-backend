package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.dtos.ReviewResultDto;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private Long orderId;

    @Embedded
    private OrderProduct orderProduct;

    private Double rating;

    private String content;

    private String image;

    @CreationTimestamp
    private LocalDateTime createAt;

    public Review() {
    }

    public Review(Long userId, Long orderId, OrderProduct orderProduct,
                  Double rating, String content, String image) {
        this.userId = userId;
        this.orderId = orderId;
        this.orderProduct = orderProduct;
        this.rating = rating;
        this.content = content;
        this.image = image;
    }

    public Review(Long id, Long userId, Long orderId,
                  OrderProduct orderProduct, Double rating,
                  String content) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.orderProduct = orderProduct;
        this.rating = rating;
        this.content = content;
    }

    public static Review fake(Long reviewId) {
        OrderProduct orderProduct = OrderProduct.fake(1L, 1L);
        return new Review(reviewId, 1L, 1L, orderProduct, 5D, "부드럽고 따뜻해요");
    }

    public Long id() {
        return id;
    }

    public Long userId() {
        return userId;
    }

    public Long orderId() {
        return orderId;
    }

    public OrderProduct orderProduct() {
        return orderProduct;
    }

    public Double rating() {
        return rating;
    }

    public String content() {
        return content;
    }

    public String image() {
        return image;
    }

    public LocalDateTime createAt() {
        return createAt;
    }

    public ReviewResultDto toResultDto() {
        return new ReviewResultDto(id);
    }
}
