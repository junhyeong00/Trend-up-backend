package com.junhyeong.shoppingmall.admin.controllers;

import com.junhyeong.shoppingmall.admin.services.CreateProductService;
import com.junhyeong.shoppingmall.dtos.ProductRequestDto;
import com.junhyeong.shoppingmall.dtos.ProductResultDto;
import com.junhyeong.shoppingmall.exceptions.CategoryNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/product")
public class AdminProductController {
    private final CreateProductService createProductService;

    public AdminProductController(CreateProductService createProductService) {
        this.createProductService = createProductService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResultDto createProduct(
            @Validated @RequestBody ProductRequestDto productRequestDto
    ) {
        return createProductService.createProduct(
                productRequestDto.getProductName(),
                productRequestDto.getCategoryId(),
                productRequestDto.getDescription(),
                productRequestDto.getPrice(),
                productRequestDto.getImageUrl(),
                productRequestDto.getOptions()
        );
    }

    @ExceptionHandler(CategoryNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String categoryNotFound(Exception e) {
        return e.getMessage();
    }
}
