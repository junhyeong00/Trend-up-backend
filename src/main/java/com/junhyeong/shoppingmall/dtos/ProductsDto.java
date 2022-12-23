package com.junhyeong.shoppingmall.dtos;

import org.springframework.data.domain.Page;

public class ProductsDto {
    private Page<ProductDto> products;
    private int totalPageCount;

    public ProductsDto(Page<ProductDto> products, int totalPageCount) {
        this.products = products;
        this.totalPageCount = totalPageCount;
    }

    public Page<ProductDto> getProducts() {
        return products;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }
}
