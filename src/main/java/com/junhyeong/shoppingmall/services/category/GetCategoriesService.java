package com.junhyeong.shoppingmall.services.category;

import com.junhyeong.shoppingmall.dtos.CategoriesDto;
import com.junhyeong.shoppingmall.dtos.CategoryDto;
import com.junhyeong.shoppingmall.models.Category;
import com.junhyeong.shoppingmall.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GetCategoriesService {
    private final CategoryRepository categoryRepository;

    public GetCategoriesService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoriesDto categories() {
        List<Category>  categories = categoryRepository.findAll();

        List<CategoryDto> categoryDtos = categories.stream().map(Category::toDto).toList();
        return new CategoriesDto(categoryDtos);
    }
}
