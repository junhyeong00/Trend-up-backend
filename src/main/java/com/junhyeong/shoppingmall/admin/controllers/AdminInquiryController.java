package com.junhyeong.shoppingmall.admin.controllers;

import com.junhyeong.shoppingmall.admin.services.GetInquiriesAdminService;
import com.junhyeong.shoppingmall.dtos.InquiriesDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminInquiryController {
    private final GetInquiriesAdminService getInquiriesAdminService;

    public AdminInquiryController(GetInquiriesAdminService getInquiriesAdminService) {
        this.getInquiriesAdminService = getInquiriesAdminService;
    }

    @GetMapping("admin-inquiries")
    public InquiriesDto inquiries(
            @PageableDefault(size = 8, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return getInquiriesAdminService.inquiries(pageable);
    }
}
