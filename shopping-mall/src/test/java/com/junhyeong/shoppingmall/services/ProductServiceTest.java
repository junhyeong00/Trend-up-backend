package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void products() {
        List<Product> products = List.of(
                new Product(1L, "남성 패션", "상품 1", "상품 설명 1", 3, 500L, null),
                new Product(2L, "남성 패션", "상품 2", "상품 설명 2", 4, 5000L, null),
                new Product(2L, "남성 패션", "상품 3", "상품 설명 3", 4, 5000L, null)
        );

        int page = 1;

        Page<Product> pageableProducts
                = new PageImpl<>(products, PageRequest.of(page - 1, 2), products.size());

        given(productRepository.findAll(any(Pageable.class))).willReturn(pageableProducts);

        productService.products(page);

        assertThat(pageableProducts).hasSize(products.size());

        verify(productRepository).findAll(any(Pageable.class));
    }
}
