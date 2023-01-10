package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.ProductDto;
import com.junhyeong.shoppingmall.dtos.ProductsDto;
import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.services.GetProductService;
import com.junhyeong.shoppingmall.services.GetProductsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            @RequestParam(required = false) String keyword
    ) {
        return getProductsService.products(page, categoryId, keyword);
    }

    @GetMapping("{id}")
    public ProductDto product(
            @PathVariable("id") Long productId
    ) {
        return getProductService.product(productId);
    }
}
