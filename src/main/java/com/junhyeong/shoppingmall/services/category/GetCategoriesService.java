package com.junhyeong.shoppingmall.services.category;

import com.junhyeong.shoppingmall.dtos.CategoriesDto;
import com.junhyeong.shoppingmall.dtos.CategoryDto;
import com.junhyeong.shoppingmall.models.category.Category;
import com.junhyeong.shoppingmall.repositories.CategoryRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetCategoriesService {
    private final CategoryRepository categoryRepository;

    public GetCategoriesService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
//    @Cacheable(value = "categoriesCache", cacheManager = "redisCacheManager")
    public CategoriesDto categories() {
        List<Category>  categories = categoryRepository.findAll();

        List<CategoryDto> categoryDtos = categories.stream().map(Category::toDto).toList();
        return new CategoriesDto(categoryDtos);
    }
}
