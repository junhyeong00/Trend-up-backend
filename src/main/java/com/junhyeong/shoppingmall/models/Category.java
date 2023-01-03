package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.dtos.CategoryDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category fake(Long categoryId) {
        return new Category(categoryId, "상의");
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public CategoryDto toDto() {
        return new CategoryDto(id, name);
    }
}
