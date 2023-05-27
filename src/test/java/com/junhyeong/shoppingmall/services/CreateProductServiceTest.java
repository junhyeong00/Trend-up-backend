package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.admin.services.CreateProductService;
import com.junhyeong.shoppingmall.dtos.CreateOptionDto;
import com.junhyeong.shoppingmall.dtos.ProductResultDto;
import com.junhyeong.shoppingmall.models.option.Option;
import com.junhyeong.shoppingmall.models.product.Product;
import com.junhyeong.shoppingmall.repositories.CategoryRepository;
import com.junhyeong.shoppingmall.repositories.OptionRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateProductServiceTest {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private OptionRepository optionRepository;
    private CreateProductService createProductService;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        optionRepository = mock(OptionRepository.class);
        createProductService = new CreateProductService(productRepository, categoryRepository, optionRepository);
    }

    @Test
    void createProduct() {
        Long categoryId = 1L;
        Long productId = 1L;

        List<CreateOptionDto> options = List.of(
                new CreateOptionDto("두툼한", 10000L)
        );

        given(categoryRepository.existsById(categoryId)).willReturn(true);

        ProductResultDto productResultDto = createProductService
                .createProduct("가디건", categoryId, "부드럽다", 10000L, "", options);

        assertThat(productResultDto).isNotNull();

        verify(categoryRepository).existsById(categoryId);
        verify(productRepository).save(any(Product.class));
        verify(optionRepository).save(any(Option.class));
    }
}