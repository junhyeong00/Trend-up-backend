package com.junhyeong.shoppingmall.admin.controllers;

import com.junhyeong.shoppingmall.admin.services.GetDeliveryInformationService;
import com.junhyeong.shoppingmall.admin.services.GetOrdersAdminService;
import com.junhyeong.shoppingmall.admin.services.GetSalesService;
import com.junhyeong.shoppingmall.admin.services.UpdateDeliveryStatusService;
import com.junhyeong.shoppingmall.models.order.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminOrderController.class)
@ActiveProfiles("test")
class AdminOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetOrdersAdminService getOrdersAdminService;

    @MockBean
    private UpdateDeliveryStatusService updateDeliveryStatusService;

    @MockBean
    private GetSalesService getSalesService;

    @MockBean
    private GetDeliveryInformationService getDeliveryInformationService;

    @Test
    void orders() throws Exception {
        Long orderId = 1L;
        List<Order> orders = List.of(
                Order.fake(orderId)
        );

        int page = 1;

        Page<Order> pageableOrders
                = new PageImpl<>(orders, PageRequest.of(page - 1, 2), orders.size());
        given(getOrdersAdminService.orders(any()))
                .willReturn(pageableOrders);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/orders")
                        .param("page", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void changeDeliveryStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"deliveryStatus\": \"배송중\"" +
                                "}"))
                .andExpect(status().isNoContent());

        verify(updateDeliveryStatusService).changeDeliveryStatus(any(), any());
    }

    @Test
    void sales() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/orders/sales"))
                .andExpect(status().isOk());

        verify(getSalesService).sales();
    }

    @Test
    void deliveryInformation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/orders/delivery"))
                .andExpect(status().isOk());

        verify(getDeliveryInformationService).deliveryInformation();
    }
}
