package com.junhyeong.shoppingmall.dtos;

import java.util.List;

public class CategoriesDto {
    private List<CategoryDto> categories;

    public CategoriesDto() {
    }

    public CategoriesDto(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }
}
