package com.junhyeong.shoppingmall.dtos;


import java.util.List;

public class ProductsDto {
    private List<ProductDto> products;
    private int totalPageCount;

    public ProductsDto() {
    }

    public ProductsDto(List<ProductDto> products, int totalPageCount) {
        this.products = products;
        this.totalPageCount = totalPageCount;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }
}
