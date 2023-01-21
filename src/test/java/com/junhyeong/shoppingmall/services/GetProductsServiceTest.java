package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.Category;
import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.repositories.CategoryRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetProductsServiceTest {
    private ProductRepository productRepository;
    private GetProductsService getProductsService;
    private CategoryRepository categoryRepository;
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        reviewRepository = mock(ReviewRepository.class);
        getProductsService = new GetProductsService(productRepository, categoryRepository, reviewRepository);
    }

    @Test
    void products() {
        Long categoryId = 1L;

        List<Product> products = List.of(
                new Product(1L, categoryId, "상품 1", "상품 설명 1", 500L, null),
                new Product(2L, categoryId, "상품 2", "상품 설명 2", 5000L, null),
                new Product(2L, categoryId, "상품 3", "상품 설명 3", 5000L, null)
        );

        int page = 1;

        Page<Product> pageableProducts
                = new PageImpl<>(products, PageRequest.of(page - 1, 2), products.size());

        given(productRepository.findAll(any(Specification.class), any(Pageable.class))).willReturn(pageableProducts);

        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(Category.fake(categoryId)));

        String keyword = null;

        getProductsService.products(page, categoryId, keyword);

        assertThat(pageableProducts).hasSize(products.size());

        verify(productRepository).findAll(any(Specification.class), any(Pageable.class));
    }
}
