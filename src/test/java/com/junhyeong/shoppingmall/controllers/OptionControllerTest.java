package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.services.GetOptionsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OptionController.class)
@ActiveProfiles("test")
class OptionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetOptionsService getOptionsService;

    @Test
    void options() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1/options"))
                .andExpect(status().isOk());
    }
}
