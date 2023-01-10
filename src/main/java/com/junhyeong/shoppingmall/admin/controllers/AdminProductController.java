package com.junhyeong.shoppingmall.admin.controllers;

import com.junhyeong.shoppingmall.dtos.ProductRequestDto;
import com.junhyeong.shoppingmall.dtos.ProductResultDto;
import com.junhyeong.shoppingmall.admin.services.CreateProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminProductController {
    private final CreateProductService createProductService;

    public AdminProductController(CreateProductService createProductService) {
        this.createProductService = createProductService;
    }

    @PostMapping("product")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResultDto createProduct(
            @RequestBody ProductRequestDto productRequestDto
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
}
