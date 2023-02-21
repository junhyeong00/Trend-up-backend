package com.junhyeong.shoppingmall.admin.controllers;

import com.junhyeong.shoppingmall.admin.services.GetInquiriesAdminService;
import com.junhyeong.shoppingmall.controllers.InquiryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminInquiryController.class)
@ActiveProfiles("test")
class AdminInquiryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetInquiriesAdminService getInquiriesAdminService;

    @Test
    void inquiries() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/inquiries"))
                .andExpect(status().isOk());

        verify(getInquiriesAdminService).inquiries(any());
    }
}
