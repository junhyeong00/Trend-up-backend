package com.junhyeong.shoppingmall.backdoor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BackdoorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void setupProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/setup-products"))
                .andExpect(status().isOk());
    }

    @Test
    void setupUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/setup-user"))
                .andExpect(status().isOk());
    }

    @Test
    void setupReviews() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/setup-reviews"))
                .andExpect(status().isOk());
    }

    @Test
    void setupInquiries() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/backdoor/setup-inquiries"))
                .andExpect(status().isOk());
    }
}
