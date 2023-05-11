package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.ProductDto;
import com.junhyeong.shoppingmall.models.category.Category;
import com.junhyeong.shoppingmall.models.product.Product;
import com.junhyeong.shoppingmall.repositories.CategoryRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.services.product.GetProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetProductServiceTest {
    private ProductRepository productRepository;
    private GetProductService getProductService;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        getProductService = new GetProductService(productRepository, categoryRepository);
    }

    @Test
    void product() {
        Long categoryId = 1L;

        Product product = new Product(1L, categoryId, "상품 1", "상품 설명 1", 500L, null);

        given(productRepository.findById(product.id())).willReturn(Optional.of(product));

        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(Category.fake(categoryId)));

        ProductDto found = getProductService.product(product.id());

        assertThat(found).isNotNull();

        verify(productRepository).findById(product.id());
    }
}
