package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.CategoriesDto;
import com.junhyeong.shoppingmall.dtos.OptionDto;
import com.junhyeong.shoppingmall.dtos.OptionsDto;
import com.junhyeong.shoppingmall.models.Category;
import com.junhyeong.shoppingmall.models.Option;
import com.junhyeong.shoppingmall.services.GetCategoriesService;
import com.junhyeong.shoppingmall.services.GetOptionsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
