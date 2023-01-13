package com.junhyeong.shoppingmall.admin.controllers;

import com.junhyeong.shoppingmall.admin.services.CreateAnswerService;
import com.junhyeong.shoppingmall.exceptions.AnswerWriteFailed;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminAnswerController.class)
@ActiveProfiles("test")
class AdminAnswerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateAnswerService createAnswerService;

    @Test
    void write() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"inquiryId\": \"1\"," +
                                "\"comment\":\"해당 상품은 일주일 내로 재입고 될 예정입니다\"" +
                                "}"))
                .andExpect(status().isCreated());

        verify(createAnswerService).write(any(), any());
    }

    @Test
    void writeWithExistsAnswer() throws Exception {
        given(createAnswerService.write(any(),any())).willThrow(new AnswerWriteFailed());

        mockMvc.perform(MockMvcRequestBuilders.post("/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"inquiryId\": \"1\"," +
                                "\"comment\":\"해당 상품은 일주일 내로 재입고 될 예정입니다\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void writeWithBlankComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"inquiryId\": \"1\"," +
                                "}"))
                .andExpect(status().isBadRequest());
    }
}
