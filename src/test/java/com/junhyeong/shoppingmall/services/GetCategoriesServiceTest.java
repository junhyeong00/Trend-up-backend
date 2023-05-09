package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.CategoriesDto;
import com.junhyeong.shoppingmall.models.Category;
import com.junhyeong.shoppingmall.repositories.CategoryRepository;
import com.junhyeong.shoppingmall.services.category.GetCategoriesService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetCategoriesServiceTest {
    @Test
    void categories() {
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        GetCategoriesService getCategoriesService = new GetCategoriesService(categoryRepository);

        given(categoryRepository.findAll())
                .willReturn(List.of(
                        new Category(1L, "상의"),
                        new Category(2L, "하의"),
                        new Category(3L, "패딩")
                ));

        CategoriesDto categoriesDto = getCategoriesService.categories();

        assertThat(categoriesDto).isNotNull();

        verify(categoryRepository).findAll();
    }
}
