package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.config.MockMvcEncoding;
import com.junhyeong.shoppingmall.exceptions.LoginFailed;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.services.LoginService;
import com.junhyeong.shoppingmall.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcEncoding
@WebMvcTest(SessionController.class)
@ActiveProfiles("test")
class SessionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @SpyBean
    private JwtUtil jwtUtil;

    private UserName userName;

    @BeforeEach
    void setUp() {
        userName = new UserName("test123");
        User user = User.fake(userName);

        given(loginService.login(userName, "Password1234!")).willReturn(user);

        given(loginService.login(userName, "xxx")).willThrow(new LoginFailed("비밀번호가 일치하지 않습니다"));
        given(loginService.login(new UserName("xxx"), "Password1234!")).willThrow(new LoginFailed("존재하지 않는 아이디입니다"));
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"userName\":\"test123\"," +
                                "\"password\":\"Password1234!\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("name")
                ));

        verify(loginService).login(any(), any());

    }

    @Test
    void loginWithWrongUserName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"userName\":\"xxx\"," +
                                "\"password\":\"Password1234!\"" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString("존재하지 않는 아이디입니다")
                ));

        verify(loginService, never()).login(new UserName("xxx"), "password1234!");
    }

    @Test
    void loginWithWrongPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"userName\":\"test123\"," +
                                "\"password\":\"xxx\"" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString("비밀번호가 일치하지 않습니다")
                ));

        verify(loginService, never()).login(userName, "xx");
    }

    @Test
    void loginWithBlankUserName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"userName\":\"\"," +
                                "\"password\":\"Password1234!\"" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString("아이디를 입력해주세요")
                ));

        verify(loginService, never()).login(any(), any());
    }

    @Test
    void loginWithBlankPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"userName\":\"test123\"," +
                                "\"password\":\"\"" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString("비밀번호를 입력해주세요")
                ));

        verify(loginService, never()).login(any(), any());
    }
}
