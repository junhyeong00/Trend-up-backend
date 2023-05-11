package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.InquiriesDto;
import com.junhyeong.shoppingmall.models.inquiry.Content;
import com.junhyeong.shoppingmall.models.inquiry.Inquiry;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.inquiry.Title;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.AnswerRepository;
import com.junhyeong.shoppingmall.repositories.InquiryRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.services.inquiry.GetInquiresService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetInquiresServiceTest {
    private UserRepository userRepository;
    private InquiryRepository inquiryRepository;
    private AnswerRepository answerRepository;
    private GetInquiresService getInquiresService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        inquiryRepository = mock(InquiryRepository.class);
        answerRepository = mock(AnswerRepository.class);
        getInquiresService = new GetInquiresService(userRepository, inquiryRepository, answerRepository);
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

        UserName userName = new UserName("test123");

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        given(userRepository.findById(userId))
                .willReturn(Optional.of(User.fake(userName)));

        given(inquiryRepository.findAllByProductId(productId, pageable))
                .willReturn(pageableInquiries);

        InquiriesDto inquiriesDto = getInquiresService.inquiries(productId, userName, pageable);

        assertThat(inquiriesDto).isNotNull();

        verify(userRepository).findByUserName(userName);
        verify(inquiryRepository).findAllByProductId(productId, pageable);
    }
}
