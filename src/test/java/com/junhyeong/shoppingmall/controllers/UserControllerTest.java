package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.UserName;
import com.junhyeong.shoppingmall.services.GetUserService;
import com.junhyeong.shoppingmall.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetUserService getUserService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String token;
    private UserName userName;

    @BeforeEach
    void setup() {
        userName = new UserName("test123");
        token = jwtUtil.encode(userName);
    }

    @Test
    void user() throws Exception {
        given(getUserService.user(userName))
                .willReturn(User.fake(userName));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        verify(getUserService).user(userName);
    }
}
