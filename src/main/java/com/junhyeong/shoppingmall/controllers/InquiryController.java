package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.InquiryRequestDto;
import com.junhyeong.shoppingmall.dtos.InquiryResultDto;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.services.CreateInquiryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InquiryController {
    private final CreateInquiryService createInquiryService;

    public InquiryController(CreateInquiryService createInquiryService) {
        this.createInquiryService = createInquiryService;
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
