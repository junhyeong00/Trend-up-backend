package com.junhyeong.shoppingmall.dtos;

public class ProductDto {
    private Long id;
    private Long categoryId;
    private String name;
    private String description;
    private Long price;
    private String image;

    public ProductDto(Long id, Long category, String name, String description, Long price, String image) {
        this.id = id;
        this.categoryId = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public Long getCategory() {
        return categoryId;
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
