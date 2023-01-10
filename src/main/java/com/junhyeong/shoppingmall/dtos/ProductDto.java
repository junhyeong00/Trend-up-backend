package com.junhyeong.shoppingmall.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ProductDto {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String name;
    private String description;
    private Long price;
    private String image;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createAt;

    public ProductDto(Long id, Long category, String categoryName, String name, String description, Long price, String image, LocalDateTime createAt) {
        this.id = id;
        this.categoryId = category;
        this.categoryName = categoryName;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
