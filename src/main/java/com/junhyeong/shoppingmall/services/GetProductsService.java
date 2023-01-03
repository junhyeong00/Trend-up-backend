package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.specifications.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GetProductsService {
    private ProductRepository productRepository;

    public GetProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> products(Integer page, Long categoryId, String keyword) {
        Pageable pageable = PageRequest.of(page -1, 8);

        Specification<Product> spec = (root, query, builder) -> null;

        if (categoryId != 0) {
            spec = spec.and(ProductSpecification.equalCategoryId(categoryId));
        }

        if (keyword != null) {
            spec = spec.and(ProductSpecification.likeProductName(keyword));
        }

        return productRepository.findAll(spec, pageable);
    }
}
