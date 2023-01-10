package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.dtos.CreateOptionDto;
import com.junhyeong.shoppingmall.dtos.ProductResultDto;
import com.junhyeong.shoppingmall.exceptions.CategoryNotFound;
import com.junhyeong.shoppingmall.models.Option;
import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.repositories.CategoryRepository;
import com.junhyeong.shoppingmall.repositories.OptionRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CreateProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;


    public CreateProductService(ProductRepository productRepository,
                                CategoryRepository categoryRepository,
                                OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.optionRepository = optionRepository;
    }

    public ProductResultDto createProduct(String productName, Long categoryId, String description,
                                          Long price, String image, List<CreateOptionDto> optionDtos) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFound();
        }

        Product product = new Product(categoryId, productName, description, price, image);

        productRepository.save(product);

        for (CreateOptionDto optionDto : optionDtos) {
            Option option = new Option(product.id(), optionDto.getOptionName(), optionDto.getOptionPrice());

            optionRepository.save(option);
        }

        return product.toResultDto();
    }
}
