package com.junhyeong.shoppingmall.dtos;

public class ProductDto {
    private Long id;
    private String category;
    private String name;
    private String description;
    private Integer productCount;
    private Long price;
    private String image;

    public ProductDto(Long id, String category, String name, String description, Integer productCount, Long price, String image) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.description = description;
        this.productCount = productCount;
        this.price = price;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public Long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
