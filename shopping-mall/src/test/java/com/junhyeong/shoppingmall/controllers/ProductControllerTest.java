package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void products() throws Exception {
        List<Product> products = List.of(
                new Product(1L, "남성 패션", "상품 1", "상품 설명 1", 3, 500L, null),
                new Product(2L, "남성 패션", "상품 2", "상품 설명 2", 4, 5000L, null),
                new Product(2L, "남성 패션", "상품 3", "상품 설명 3", 4, 5000L, null)
        );

        int page = 1;

        Page<Product> pageableProducts
                = new PageImpl<>(products, PageRequest.of(page - 1, 2), products.size());

        given(productService.products(page))
                .willReturn(pageableProducts);

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .param("page", "1"))
                .andExpect(status().isOk());

        verify(productService).products(page);
    }

    @Test
    void product() throws Exception {
        Long productId = 1L;
        Product product = new Product(productId, "남성 패션", "상품 1", "상품 설명 1", 3, 500L, null);

        given(productService.product(productId)).willReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(status().isOk());

        verify(productService).product(productId);
    }
}
