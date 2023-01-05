package com.junhyeong.shoppingmall.dtos;

import java.util.List;

public class InquiriesDto {
    private List<InquiryDto> inquiries;
    private int totalPageCount;

    public InquiriesDto(List<InquiryDto> inquiries, int totalPageCount) {
        this.inquiries = inquiries;
        this.totalPageCount = totalPageCount;
    }

    public List<InquiryDto> getInquiries() {
        return inquiries;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }
}
