package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.CategoriesDto;
import com.junhyeong.shoppingmall.services.category.GetCategoriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    private final GetCategoriesService getCategoriesService;

    public CategoryController(GetCategoriesService getCategoriesService) {
        this.getCategoriesService = getCategoriesService;
    }

    @GetMapping("categories")
    public CategoriesDto categories() {
       return getCategoriesService.categories();
    }
}
