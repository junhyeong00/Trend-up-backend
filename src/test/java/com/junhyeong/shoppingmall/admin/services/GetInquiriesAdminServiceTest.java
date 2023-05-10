package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.dtos.InquiriesDto;
import com.junhyeong.shoppingmall.models.inquiry.Content;
import com.junhyeong.shoppingmall.models.inquiry.Inquiry;
import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.inquiry.Title;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.AnswerRepository;
import com.junhyeong.shoppingmall.repositories.InquiryRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetInquiriesAdminServiceTest {
    private InquiryRepository inquiryRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private AnswerRepository answerRepository;
    private GetInquiriesAdminService getInquiriesAdminService;

    @BeforeEach
    void setup() {
        inquiryRepository = mock(InquiryRepository.class);
        userRepository = mock(UserRepository.class);
        productRepository = mock(ProductRepository.class);
        answerRepository = mock(AnswerRepository.class);
        getInquiriesAdminService = new GetInquiriesAdminService(
                inquiryRepository, userRepository, productRepository, answerRepository);
    }

    @Test
    void inquiries() {
        Long productId = 1L;
        Long userId = 1L;

        List<Inquiry> inquiries = List.of(
                new Inquiry(1L, productId, userId, new Title("재입고 질문"), new Content("재입고 언제 될까요?"), false),
                new Inquiry(2L, productId, userId, new Title("재입고 질문"), new Content("재입고 언제 될까요?"), true),
                new Inquiry(3L, productId, userId, new Title("재입고 질문"), new Content("재입고 언제 될까요?"), false)
        );

        int page = 1;

        Pageable pageable = PageRequest.of(page - 1, 8, Sort.by("createAt").descending());

        Page<Inquiry> pageableInquiries = new PageImpl<>(inquiries, pageable, inquiries.size());

        given(inquiryRepository.findAll(pageable))
                .willReturn(pageableInquiries);

        UserName userName = new UserName("test123");

        given(userRepository.findById(userId))
                .willReturn(Optional.of(User.fake(userName)));

        given(productRepository.findById(productId))
                .willReturn(Optional.of(Product.fake(productId)));

        InquiriesDto inquiriesDto = getInquiriesAdminService.inquiries(pageable);

        assertThat(inquiriesDto).isNotNull();

        verify(inquiryRepository).findAll(pageable);
    }
}
