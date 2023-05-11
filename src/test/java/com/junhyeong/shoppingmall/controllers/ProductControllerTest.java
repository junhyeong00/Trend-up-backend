package com.junhyeong.shoppingmall.controllers;

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
        Long categoryId = 0L;

        List<Product> products = List.of(
                new Product(1L, categoryId, "상품 1", "상품 설명 1", 500L, null),
                new Product(2L, categoryId, "상품 2", "상품 설명 2", 5000L, null),
                new Product(2L, categoryId, "상품 3", "상품 설명 3", 5000L, null)
        );

        int page = 1;

        Page<Product> pageableProducts
                = new PageImpl<>(products, PageRequest.of(page - 1, 2), products.size());

        String keyword = null;

//        given(getProductsService.products(page, categoryId, keyword))
//                .willReturn(new ProductsDto(any(), any()));

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .param("page", "1"))
                .andExpect(status().isOk());

        verify(getProductsService).products(page, categoryId, keyword);
    }

    @Test
    void product() throws Exception {
        Long productId = 1L;
        Long categoryId = 1L;

        Product product = new Product(productId, categoryId, "상품 1", "상품 설명 1", 500L, null);

        given(getProductService.product(productId)).willReturn(product.toDto("상의", 5, 3L));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(status().isOk());

        verify(getProductService).product(productId);
    }
}
