package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.InquiryResultDto;
import com.junhyeong.shoppingmall.models.Inquiry;
import com.junhyeong.shoppingmall.models.Review;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.services.CreateInquiryService;
import com.junhyeong.shoppingmall.services.DeleteInquiryService;
import com.junhyeong.shoppingmall.services.GetInquiryService;
import com.junhyeong.shoppingmall.services.UpdateInquiryService;
import com.junhyeong.shoppingmall.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InquiryController.class)
@ActiveProfiles("test")
class InquiryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateInquiryService createInquiryService;

    @MockBean
    private GetInquiryService getInquiryService;

    @MockBean
    private DeleteInquiryService deleteInquiryService;

    @MockBean
    private UpdateInquiryService updateInquiryService;

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
    void write() throws Exception {
        given(createInquiryService.write(any(), any(), any(), any(), any()))
                .willReturn(new InquiryResultDto(1L));

        mockMvc.perform(MockMvcRequestBuilders.post("/inquiry")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":\"1\", " +
                                "\"title\":\"재입고 질문\", " +
                                "\"content\":\"재입고 언제 될까요?\", " +
                                "\"isSecret\":\"false\"" +
                                "}"))
                .andExpect(status().isCreated());

        verify(createInquiryService).write(any(), any(), any(), any(), any());
    }

    @Test
    void inquiries() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1/inquiries")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        verify(getInquiryService).inquiries(any(), any(), any());
    }

    @Test
    void delete() throws Exception {
        Long inquiryId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/inquiries/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        verify(deleteInquiryService).delete(userName, inquiryId);
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/inquiries/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"재입고 질문\", " +
                                "\"content\":\"재입고 언제 될까요?\", " +
                                "\"isSecret\":\"false\"" +
                                "}"))
                .andExpect(status().isNoContent());

        verify(updateInquiryService).update(any(), any(), any(), any(), any());
    }
}
