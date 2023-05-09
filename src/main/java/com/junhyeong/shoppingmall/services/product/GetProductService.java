package com.junhyeong.shoppingmall.services.product;

import com.junhyeong.shoppingmall.dtos.ProductDto;
import com.junhyeong.shoppingmall.exceptions.CategoryNotFound;
import com.junhyeong.shoppingmall.exceptions.ProductNotFound;
import com.junhyeong.shoppingmall.models.Category;
import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.repositories.CategoryRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GetProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public GetProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductDto product(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFound::new);

        Category category = categoryRepository.findById(product.categoryId()).orElseThrow(CategoryNotFound::new);
        return product.toDto(category.name());
    }
}
