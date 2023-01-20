package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.dtos.CreateOptionDto;
import com.junhyeong.shoppingmall.dtos.ProductResultDto;
import com.junhyeong.shoppingmall.repositories.OptionRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UpdateProductService {
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public UpdateProductService(ProductRepository productRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    public ProductResultDto updateProduct(String productName, Long categoryId, String description,
                                          Long price, String imageUrl, List<CreateOptionDto> options) {
        return null;
    }
}
