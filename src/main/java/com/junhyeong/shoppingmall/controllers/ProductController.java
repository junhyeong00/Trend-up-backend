package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.ProductDetailDto;
import com.junhyeong.shoppingmall.dtos.ProductDto;
import com.junhyeong.shoppingmall.dtos.ProductsDto;
import com.junhyeong.shoppingmall.exceptions.CategoryNotFound;
import com.junhyeong.shoppingmall.services.product.GetProductService;
import com.junhyeong.shoppingmall.services.product.GetProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {
    private final GetProductService getProductService;
    private final GetProductsService getProductsService;

    public ProductController(GetProductService getProductService,
                             GetProductsService getProductsService) {
        this.getProductService = getProductService;
        this.getProductsService = getProductsService;
    }

    @GetMapping
    public ProductsDto products(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "0") Long categoryId,
            @RequestParam(defaultValue = "") String keyword
    ) {
        return getProductsService.products(page, categoryId, keyword);
    }

    @GetMapping("{id}")
    public ProductDetailDto product(
            @PathVariable("id") Long productId
    ) {
        return getProductService.product(productId);
    }

    @ExceptionHandler(CategoryNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String categoryNotFound(Exception e) {
        return e.getMessage();
    }
}
