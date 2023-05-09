package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.InquiryResultDto;
import com.junhyeong.shoppingmall.models.Inquiry;
import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.InquiryRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.services.inquiry.CreateInquiryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateInquiryServiceTest {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private InquiryRepository inquiryRepository;
    private CreateInquiryService createInquiryService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        productRepository = mock(ProductRepository.class);
        inquiryRepository = mock(InquiryRepository.class);
        createInquiryService = new CreateInquiryService(userRepository, productRepository, inquiryRepository);
    }

    @Test
    void write() {
        UserName userName = new UserName("test123");
        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        Long productId = 1L;

        given(productRepository.findById(productId))
                .willReturn(Optional.of(Product.fake(productId)));

        Long inquiryId = 1L;

        given(inquiryRepository.save(Inquiry.fake(inquiryId)))
                .willReturn(Inquiry.fake(inquiryId));

        InquiryResultDto inquiryResultDto =
                createInquiryService.write(userName, productId, "재입고 질문", "재입고 언제 될까요?", false);

        assertThat(inquiryResultDto).isNotNull();
    }
}
