package com.junhyeong.shoppingmall.dtos;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class ProductRequestDto {
    @NotBlank
    private String productName;

    private Long categoryId;

    private Long price;

    private List<CreateOptionDto> options;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String productName, Long categoryId, Long price,
                             List<CreateOptionDto> options, String description, String imageUrl) {
        this.productName = productName;
        this.categoryId = categoryId;
        this.price = price;
        this.options = options;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getPrice() {
        return price;
    }

    public List<CreateOptionDto> getOptions() {
        return options;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
