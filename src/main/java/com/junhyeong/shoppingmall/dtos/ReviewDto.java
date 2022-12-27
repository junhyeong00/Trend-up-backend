package com.junhyeong.shoppingmall.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.junhyeong.shoppingmall.models.UserName;

import java.time.LocalDateTime;

public class ReviewDto {
    private Long id;
    private Double rating;
    private String content;
    private String image;
    private String productName;
    private String productOption;
    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createAt;

    public ReviewDto(Long id, Double rating, String content, String image,
                     String productName, String productOption, UserName userName,
                     LocalDateTime createAt) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.image = image;
        this.productName = productName;
        this.productOption = productOption;
        this.userName = userName.value();
        this.createAt = createAt;
    }

    public ReviewDto(Long id, Double rating, String content, String image,
                     String productName, String productOption,
                     LocalDateTime createAt) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.image = image;
        this.productName = productName;
        this.productOption = productOption;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public Double getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductOption() {
        return productOption;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
}
