package com.junhyeong.shoppingmall.admin.controllers;

import com.junhyeong.shoppingmall.admin.services.CreateProductService;
import com.junhyeong.shoppingmall.admin.services.UpdateProductService;
import com.junhyeong.shoppingmall.dtos.ProductResultDto;
import com.junhyeong.shoppingmall.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminProductController.class)
@ActiveProfiles("test")
class AdminProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateProductService createProductService;

    @MockBean
    private UpdateProductService updateProductService;

    @Test
    void createProduct() throws Exception {
        given(createProductService.createProduct(any(), any(), any(), any(), any(), any()))
                .willReturn(new ProductResultDto(1L));

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productName\":\"가디건\"," +
                                "\"categoryId\":\"1\"," +
                                "\"price\":\"10000\"," +
                                "\"description\":\"부드럽다\"," +
                                "\"imageUrl\":\"imageUrl\"" +
                                "}"))
                .andExpect(status().isCreated());

        verify(createProductService).createProduct(any(), any(), any(), any(), any(), any());
    }
}
