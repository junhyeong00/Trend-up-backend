package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> products(Integer page) {
        Pageable pageable = PageRequest.of(page -1, 8);
        return productRepository.findAll(pageable);
    }
}
