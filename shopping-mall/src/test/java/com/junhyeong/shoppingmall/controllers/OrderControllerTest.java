package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.exceptions.OrderFailed;
import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.models.UserName;
import com.junhyeong.shoppingmall.services.CreateOrderService;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ActiveProfiles("test")
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateOrderService createOrderService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setup() {
        UserName userName = new UserName("test123");
        token = jwtUtil.encode(userName);
    }

    @Test
    void createOrder() throws Exception {
        Long orderId = 1L;
        given(createOrderService.createOrder(any(),any(),any(),any(),any(),any(),any(),any(),any()))
                .willReturn(Order.fake(orderId));

        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"receiver\":\"배준형\"," +
                                "\"phoneNumber\":\"01012341234\"," +
                                "\"payment\":\"20000\"," +
                                "\"totalPrice\":\"17000\"," +
                                "\"deliveryFee\":\"3000\"," +
                                "\"deliveryRequest\":\"안전 배송해주세요\"," +
                                "\"zipCode\":\"123\"," +
                                "\"roadAddress\":\"뉴욕\"," +
                                "\"detailAddress\":\"뉴욕\"," +
                                "\"orderProductDtos\":" +
                                "{" +
                                "\"productId\":\"1\"," +
                                "\"optionId\":\"1\"," +
                                "\"quantity\":\"2\"" +
                                "}" +
                                "}"))
                .andExpect(status().isCreated());

        verify(createOrderService).createOrder(any(),any(),any(),any(),any(),any(),any(),any(),any());
    }

    @Test
    void createOrderWithBlankReceiver() throws Exception {
        Long orderId = 1L;
        given(createOrderService.createOrder(any(),any(),any(),any(),any(),any(),any(),any(),any()))
                .willThrow(new OrderFailed("받는 분 성함을 입력해주세요"));

        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"receiver\":\"\"," +
                                "\"phoneNumber\":\"01012341234\"," +
                                "\"payment\":\"20000\"," +
                                "\"totalPrice\":\"17000\"," +
                                "\"deliveryFee\":\"3000\"," +
                                "\"deliveryRequest\":\"안전 배송해주세요\"," +
                                "\"zipCode\":\"123\"," +
                                "\"roadAddress\":\"뉴욕\"," +
                                "\"detailAddress\":\"뉴욕\"," +
                                "\"orderProductDtos\":" +
                                "{" +
                                "\"productId\":\"1\"," +
                                "\"optionId\":\"1\"," +
                                "\"quantity\":\"2\"" +
                                "}" +
                                "}"))
                .andExpect(status().isBadRequest());

        verify(createOrderService, never()).createOrder(any(),any(),any(),any(),any(),any(),any(),any(),any());
    }

    @Test
    void createOrderWithBlankPhoneNumber() throws Exception {
        Long orderId = 1L;
        given(createOrderService.createOrder(any(),any(),any(),any(),any(),any(),any(),any(),any()))
                .willThrow(new OrderFailed("받는 분 번호를 입력해주세요"));

        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"receiver\":\"배준형\"," +
                                "\"phoneNumber\":\"\"," +
                                "\"payment\":\"20000\"," +
                                "\"totalPrice\":\"17000\"," +
                                "\"deliveryFee\":\"3000\"," +
                                "\"deliveryRequest\":\"안전 배송해주세요\"," +
                                "\"zipCode\":\"123\"," +
                                "\"roadAddress\":\"뉴욕\"," +
                                "\"detailAddress\":\"뉴욕\"," +
                                "\"orderProductDtos\":" +
                                "{" +
                                "\"productId\":\"1\"," +
                                "\"optionId\":\"1\"," +
                                "\"quantity\":\"2\"" +
                                "}" +
                                "}"))
                .andExpect(status().isBadRequest());

        verify(createOrderService, never()).createOrder(any(),any(),any(),any(),any(),any(),any(),any(),any());
    }

    @Test
    void createOrderWithBlankAddress() throws Exception {
        Long orderId = 1L;
        given(createOrderService.createOrder(any(),any(),any(),any(),any(),any(),any(),any(),any()))
                .willThrow(new OrderFailed("주소를 입력해주세요"));

        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"receiver\":\"\"," +
                                "\"phoneNumber\":\"01012341234\"," +
                                "\"payment\":\"20000\"," +
                                "\"totalPrice\":\"17000\"," +
                                "\"deliveryFee\":\"3000\"," +
                                "\"deliveryRequest\":\"안전 배송해주세요\"," +
                                "\"zipCode\":\"123\"," +
                                "\"roadAddress\":\"\"," +
                                "\"detailAddress\":\"뉴욕\"," +
                                "\"orderProductDtos\":" +
                                "{" +
                                "\"productId\":\"1\"," +
                                "\"optionId\":\"1\"," +
                                "\"quantity\":\"2\"" +
                                "}" +
                                "}"))
                .andExpect(status().isBadRequest());

        verify(createOrderService, never()).createOrder(any(),any(),any(),any(),any(),any(),any(),any(),any());
    }
}
