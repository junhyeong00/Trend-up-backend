package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.InquiriesDto;
import com.junhyeong.shoppingmall.models.Inquiry;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.AnswerRepository;
import com.junhyeong.shoppingmall.repositories.InquiryRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
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
    @Test
    void inquiries() {
        UserRepository userRepository = mock(UserRepository.class);
        InquiryRepository inquiryRepository = mock(InquiryRepository.class);
        AnswerRepository answerRepository = mock(AnswerRepository.class);
        GetInquiresService getInquiresService = new GetInquiresService(userRepository, inquiryRepository, answerRepository);

        Long productId = 1L;
        Long userId = 1L;

        List<Inquiry> inquiries = List.of(
                new Inquiry(1L, productId, userId, "재입고 질문", "재입고 언제 될까요?", false),
                new Inquiry(2L, productId, userId, "재입고 질문", "재입고 언제 될까요?", true),
                new Inquiry(3L, productId, userId, "재입고 질문", "재입고 언제 될까요?", false)
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
