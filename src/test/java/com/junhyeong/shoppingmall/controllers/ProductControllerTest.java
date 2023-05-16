package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.exceptions.CategoryNotFound;
import com.junhyeong.shoppingmall.models.product.Product;
import com.junhyeong.shoppingmall.services.product.GetProductService;
import com.junhyeong.shoppingmall.services.product.GetProductsService;
import org.junit.jupiter.api.Test;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetProductService getProductService;

    @MockBean
    private GetProductsService getProductsService;

    @Test
    void products() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .param("page", "1"))
                .andExpect(status().isOk());

        verify(getProductsService).products(any(), any(), any());
    }

    @Test
    void productsWithCategoryNotFound() throws Exception {
        given(getProductsService.products(any(),any(),any()))
                .willThrow(CategoryNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .param("page", "1"))
                .andExpect(status().isNotFound());

        verify(getProductsService).products(any(), any(), any());
    }

    @Test
    void product() throws Exception {
        Long productId = 1L;
        Long categoryId = 1L;

        Product product = new Product(productId, categoryId, "상품 1", "상품 설명 1", 500L, null);

        given(getProductService.product(productId)).willReturn(product.toDetailDto("상의"));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(status().isOk());

        verify(getProductService).product(productId);
    }
}
