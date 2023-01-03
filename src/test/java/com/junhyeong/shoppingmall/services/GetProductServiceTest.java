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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetProductServiceTest {
    private ProductRepository productRepository;
    private GetProductService getProductService;

    @BeforeEach
    void setup() {

        productRepository = mock(ProductRepository.class);
        getProductService = new GetProductService(productRepository);
    }

    @Test
    void product() {
        Long categoryId = 1L;

        Product product = new Product(1L, categoryId, "상품 1", "상품 설명 1", 500L, null);

        given(productRepository.findById(product.id())).willReturn(Optional.of(product));

        Product found = getProductService.product(product.id());

        assertThat(found).isNotNull();

        verify(productRepository).findById(product.id());
    }
}
