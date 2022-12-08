package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.ProductDto;
import com.junhyeong.shoppingmall.dtos.ProductsDto;
import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.services.ProductService;
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
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ProductsDto products(
            @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        Page<Product> products = productService.products(page);

        int totalPageCount = products.getTotalPages();

        List<ProductDto> productDtos = products.stream()
                .map(Product::toDto).toList();

        Page<ProductDto> pageableProductDtos
                =new PageImpl<>(productDtos, PageRequest.of(page - 1, 8), productDtos.size());

        return new ProductsDto(pageableProductDtos, totalPageCount);
    }

    @GetMapping("{id}")
    public ProductDto product(
            @PathVariable("id") Long productId
    ) {
        Product product = productService.product(productId);
        return product.toDto();
    }
}
