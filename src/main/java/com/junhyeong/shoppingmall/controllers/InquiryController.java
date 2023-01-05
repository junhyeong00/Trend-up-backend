package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.InquiriesDto;
import com.junhyeong.shoppingmall.dtos.InquiryRequestDto;
import com.junhyeong.shoppingmall.dtos.InquiryResultDto;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.services.CreateInquiryService;
import com.junhyeong.shoppingmall.services.GetInquiryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InquiryController {
    private final CreateInquiryService createInquiryService;
    private final GetInquiryService getInquiryService;

    public InquiryController(CreateInquiryService createInquiryService,
                             GetInquiryService getInquiryService) {
        this.createInquiryService = createInquiryService;
        this.getInquiryService = getInquiryService;
    }

    @GetMapping("products/{productId}/inquiries")
    public InquiriesDto inquiries(
            @PathVariable("productId") Long productId,
            @RequestAttribute("userName") UserName userName,
            @PageableDefault(size = 8, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return getInquiryService.inquiries(productId, userName, pageable);
    }

    @PostMapping("inquiry")
    @ResponseStatus(HttpStatus.CREATED)
    public InquiryResultDto write(
            @RequestAttribute("userName") UserName userName,
            @RequestBody InquiryRequestDto inquiryRequestDto
    ) {
        return createInquiryService.write(
                userName,
                inquiryRequestDto.getProductId(),
                inquiryRequestDto.getTitle(),
                inquiryRequestDto.getContent(),
                inquiryRequestDto.getIsSecret()
        );
    }
}
