package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.dtos.ProductDto;
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

    public static Product fake(Long id) {
        Long categoryId = 1L;
        Category category = Category.fake(categoryId);
        return new Product(id, category.id(), "가디건", "",  10000L, "");
    }

    public ProductDto toDto() {
        return new ProductDto(id, categoryId, name, description, price, image);
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
}
