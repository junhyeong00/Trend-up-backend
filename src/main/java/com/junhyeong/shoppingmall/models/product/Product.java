package com.junhyeong.shoppingmall.models.product;

import com.junhyeong.shoppingmall.dtos.PopularProductDto;
import com.junhyeong.shoppingmall.dtos.ProductDetailDto;
import com.junhyeong.shoppingmall.dtos.ProductDto;
import com.junhyeong.shoppingmall.dtos.ProductResultDto;
import com.junhyeong.shoppingmall.models.category.Category;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private Long categoryId;

    private String name;

    private String description;

    private Long price;

    private String image;

    @CreationTimestamp
    private LocalDateTime createAt;

    public Product() {
    }

    public Product(Long id, Long categoryId, String name,
                   String description, Long price,
                   String image, LocalDateTime createAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.createAt = createAt;
    }

    public Product(Long id, Long categoryId, String name, String description, Long price, String image) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Product(Long categoryId, String name, String description, Long price, String image) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public static Product fake(Long id) {
        Long categoryId = 1L;
        Category category = Category.fake(categoryId);
        return new Product(id, category.id(), "가디건", "부드럽다", 10000L, "");
    }

    public ProductDto toDto(String categoryName, double totalRating, Long totalReviewCount) {
        return new ProductDto(id, categoryId, categoryName, name, price, image,
                totalRating, totalReviewCount);
    }

    public ProductDetailDto toDetailDto(String categoryName) {
        return new ProductDetailDto(id, categoryId, categoryName, name,
                description, price, image, createAt);
    }

    public Long id() {
        return id;
    }

    public Long categoryId() {
        return categoryId;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public Long price() {
        return price;
    }

    public String image() {
        return image;
    }

    public LocalDateTime createAt() {
        return createAt;
    }

    public ProductResultDto toResultDto() {
        return new ProductResultDto(id);
    }
}
