package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.exceptions.RegisterFailed;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.Cart;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.services.CreateUserService;
import com.junhyeong.shoppingmall.services.GetCartService;
import com.junhyeong.shoppingmall.services.GetUserService;
import com.junhyeong.shoppingmall.services.UpdateCartService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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

    @MockBean
    private GetCartService getCartService;

    @MockBean
    private UpdateCartService updateCartService;

    @MockBean
    private CreateUserService createUserService;

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

    @Test
    void cart() throws Exception {
        given(getCartService.cart(userName))
                .willReturn(new Cart("items").toDto());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/cart")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        verify(getCartService).cart(userName);
    }

    @Test
    void updateCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/user/cart")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"items\":\"items\"}"))
                .andExpect(status().isNoContent());

        verify(updateCartService).updateCart(userName, new Cart("items"));
    }

    @Test
    void register() throws Exception {
        given(createUserService.register(any(), any(), any(), any(), any()))
                .willReturn(User.fake(userName));

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"," +
                                "\"phoneNumber\":\"01012341234\"" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void registerWithBlankName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"," +
                                "\"phoneNumber\":\"01012341234\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithIncorrectName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"hi\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"," +
                                "\"phoneNumber\":\"01012341234\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithBlankUserName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"," +
                                "\"phoneNumber\":\"01012341234\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithIncorrectUserName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"test-123\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"," +
                                "\"phoneNumber\":\"01012341234\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithBlankPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"\"," +
                                "\"confirmPassword\":\"Password1234!\"," +
                                "\"phoneNumber\":\"01012341234\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithIncorrectPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"test\"," +
                                "\"confirmPassword\":\"test\"," +
                                "\"phoneNumber\":\"01012341234\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithBlankConfirmPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"\"," +
                                "\"phoneNumber\":\"01012341234\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithBlankPhoneNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"," +
                                "\"phoneNumber\":\"\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithIncorrectPhoneNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"," +
                                "\"phoneNumber\":\"010\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithAlreadyExistingUserName() throws Exception {
        given(createUserService.register(any(), any(), any(), any(), any()))
                .willThrow(new RegisterFailed(List.of("해당 아이디는 사용할 수 없습니다")));

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"," +
                                "\"phoneNumber\":\"01012341234\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }
}
