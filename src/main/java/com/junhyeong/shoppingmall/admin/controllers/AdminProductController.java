package com.junhyeong.shoppingmall.admin.controllers;

import com.junhyeong.shoppingmall.admin.services.UpdateProductService;
import com.junhyeong.shoppingmall.dtos.ProductRequestDto;
import com.junhyeong.shoppingmall.dtos.ProductResultDto;
import com.junhyeong.shoppingmall.admin.services.CreateProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminProductController {
    private final CreateProductService createProductService;
    private final UpdateProductService updateProductService;

    public AdminProductController(CreateProductService createProductService,
                                  UpdateProductService updateProductService) {
        this.createProductService = createProductService;
        this.updateProductService = updateProductService;
    }

    @PostMapping("product")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResultDto createProduct(
            @RequestBody ProductRequestDto productRequestDto
    ) {
        return createProductService.createProduct(
                productRequestDto.getProductName(),
                productRequestDto.getCategoryId(),
                productRequestDto.getDescription(),
                productRequestDto.getPrice(),
                productRequestDto.getImageUrl(),
                productRequestDto.getOptions()
        );
    }

//    @PatchMapping("product")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ProductResultDto updateProduct(
//            @RequestBody ProductRequestDto productRequestDto
//    ) {
//        return updateProductService.updateProduct(
//                productRequestDto.getProductName(),
//                productRequestDto.getCategoryId(),
//                productRequestDto.getDescription(),
//                productRequestDto.getPrice(),
//                productRequestDto.getImageUrl(),
//                productRequestDto.getOptions()
//        );
//    }
}
