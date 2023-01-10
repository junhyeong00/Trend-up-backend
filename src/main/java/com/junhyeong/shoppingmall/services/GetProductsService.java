package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.ProductDto;
import com.junhyeong.shoppingmall.dtos.ProductsDto;
import com.junhyeong.shoppingmall.exceptions.CategoryNotFound;
import com.junhyeong.shoppingmall.models.Category;
import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.repositories.CategoryRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.specifications.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GetProductsService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public GetProductsService(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductsDto products(Integer page, Long categoryId, String keyword) {
        Pageable pageable = PageRequest.of(page -1, 8);

        Specification<Product> spec = (root, query, builder) -> null;

        if (categoryId != 0) {
            spec = spec.and(ProductSpecification.equalCategoryId(categoryId));
        }

        if (keyword != null) {
            spec = spec.and(ProductSpecification.likeProductName(keyword));
        }

        Page<Product> products = productRepository.findAll(spec, pageable);

        int totalPageCount = products.getTotalPages();

        List<ProductDto> productDtos = products.stream()
                .map((product -> {
                    Category category = categoryRepository.findById(product.categoryId()).orElseThrow(CategoryNotFound::new);
                    return product.toDto(category.name());
                })).toList();

        Page<ProductDto> pageableProductDtos
                =new PageImpl<>(productDtos, PageRequest.of(page - 1, 8), productDtos.size());

        return new ProductsDto(pageableProductDtos, totalPageCount);
    }
}
