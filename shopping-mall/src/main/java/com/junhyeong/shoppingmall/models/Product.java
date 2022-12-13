package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.dtos.ProductDto;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String category;

    private String name;

    private String description;

    private Integer productCount;

    private Long price;

    private String image;

    @CreationTimestamp
    private LocalDateTime createAt;

    public Product() {
    }

    public Product(Long id, String category, String name,
                   String description, Integer productCount, Long price,
                   String image, LocalDateTime createAt) {
        this.id = id;
        this.category = category;
        this.name = name;

        this.description = description;
        this.productCount = productCount;
        this.price = price;
        this.image = image;
        this.createAt = createAt;
    }

    public Product(Long id, String category, String name, String description, Integer productCount, Long price, String image) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.description = description;
        this.productCount = productCount;
        this.price = price;
        this.image = image;
    }

    public static Product fake(Long id) {
        return new Product(id, "남성패션", "가디건", "", 3, 10000L, "");
    }

    public ProductDto toDto() {
        return new ProductDto(id, category, name, description, productCount, price, image);
    }

    public Long id() {
        return id;
    }

    public String category() {
        return category;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public Integer productCount() {
        return productCount;
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
