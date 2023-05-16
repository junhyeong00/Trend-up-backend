package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.CreateInquiryResultDto;
import com.junhyeong.shoppingmall.exceptions.InquiryNotFound;
import com.junhyeong.shoppingmall.exceptions.IsNotWriter;
import com.junhyeong.shoppingmall.exceptions.ProductNotFound;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.services.inquiry.CreateInquiryService;
import com.junhyeong.shoppingmall.services.inquiry.DeleteInquiryService;
import com.junhyeong.shoppingmall.services.inquiry.GetInquiresService;
import com.junhyeong.shoppingmall.services.inquiry.UpdateInquiryService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
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
    private GetInquiresService getInquiresService;

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
        given(createInquiryService.write(any(), any()))
                .willReturn(new CreateInquiryResultDto(1L));

        mockMvc.perform(MockMvcRequestBuilders.post("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":\"1\", " +
                                "\"title\":\"재입고 질문\", " +
                                "\"content\":\"재입고 언제 될까요?\", " +
                                "\"isSecret\":\"false\"" +
                                "}"))
                .andExpect(status().isCreated());

        verify(createInquiryService).write(any(), any());
    }

    @Test
    void writeWithBlankTitle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":\"1\", " +
                                "\"title\":\"\", " +
                                "\"content\":\"재입고 언제 될까요?\", " +
                                "\"isSecret\":\"false\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void writeWithBlankContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":\"1\", " +
                                "\"title\":\"재입고 질문\", " +
                                "\"content\":\"\", " +
                                "\"isSecret\":\"false\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void writeWithProductNotFound() throws Exception {
        given(createInquiryService.write(any(), any()))
                .willThrow(ProductNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":\"1\", " +
                                "\"title\":\"재입고 질문\", " +
                                "\"content\":\"재입고 언제 될까요?\", " +
                                "\"isSecret\":\"false\"" +
                                "}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void writeWithUserNotFound() throws Exception {
        given(createInquiryService.write(any(), any()))
                .willThrow(UserNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":\"1\", " +
                                "\"title\":\"재입고 질문\", " +
                                "\"content\":\"재입고 언제 될까요?\", " +
                                "\"isSecret\":\"false\"" +
                                "}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void inquiries() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1/inquiries")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        verify(getInquiresService).inquiries(any(), any(), any());
    }

    @Test
    void delete() throws Exception {
        Long inquiryId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/inquiries/%d", inquiryId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        verify(deleteInquiryService).delete(userName, inquiryId);
    }

    @Test
    void deleteWithInquiryNotFound() throws Exception {
        Long inquiryId = 999L;

        doAnswer(invocation -> {
            throw new InquiryNotFound();
        }).when(deleteInquiryService).delete(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/inquiries/%d", inquiryId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteWithUserNotFound() throws Exception {
        Long inquiryId = 1L;

        doAnswer(invocation -> {
            throw new UserNotFound();
        }).when(deleteInquiryService).delete(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/inquiries/%d", inquiryId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteWithIsNotWriter() throws Exception {
        Long inquiryId = 1L;

        doAnswer(invocation -> {
            throw new IsNotWriter();
        }).when(deleteInquiryService).delete(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/inquiries/%d", inquiryId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void update() throws Exception {
        Long inquiryId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.patch(String.format("/inquiries/%d", inquiryId))
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"재입고 질문\", " +
                                "\"content\":\"재입고 언제 될까요?\", " +
                                "\"isSecret\":\"false\"" +
                                "}"))
                .andExpect(status().isNoContent());

        verify(updateInquiryService).update(any(), any());
    }

    @Test
    void updateWithBlankTitle() throws Exception {
        Long inquiryId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.patch(String.format("/inquiries/%d", inquiryId))
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"\", " +
                                "\"content\":\"재입고 언제 될까요?\", " +
                                "\"isSecret\":\"false\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWithBlankContent() throws Exception {
        Long inquiryId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.patch(String.format("/inquiries/%d", inquiryId))
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"재입고 질문\", " +
                                "\"content\":\"\", " +
                                "\"isSecret\":\"false\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWithInquiryNotFound() throws Exception {
        Long inquiryId = 999L;

        doAnswer(invocation -> {
            throw new InquiryNotFound();
        }).when(updateInquiryService).update(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.patch(String.format("/inquiries/%d", inquiryId))
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"재입고 질문\", " +
                                "\"content\":\"재입고 언제 될까요?\", " +
                                "\"isSecret\":\"false\"" +
                                "}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateWithWriterNotFound() throws Exception {
        Long inquiryId = 1L;

        doAnswer(invocation -> {
            throw new UserNotFound();
        }).when(updateInquiryService).update(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.patch(String.format("/inquiries/%d", inquiryId))
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"재입고 질문\", " +
                                "\"content\":\"재입고 언제 될까요?\", " +
                                "\"isSecret\":\"false\"" +
                                "}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateWithIsNotWriter() throws Exception {
        Long inquiryId = 1L;

        doAnswer(invocation -> {
            throw new IsNotWriter();
        }).when(updateInquiryService).update(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.patch(String.format("/inquiries/%d", inquiryId))
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"재입고 질문\", " +
                                "\"content\":\"재입고 언제 될까요?\", " +
                                "\"isSecret\":\"false\"" +
                                "}"))
                .andExpect(status().isUnauthorized());
    }
}
