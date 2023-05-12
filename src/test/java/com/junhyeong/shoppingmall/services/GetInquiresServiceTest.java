package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.InquiriesDto;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        int page = 1;

        Pageable pageable = PageRequest.of(page - 1, 8, Sort.by("createAt").descending());

        Page<Inquiry> pageableInquiries = new PageImpl<>(List.of(Inquiry.fake(productId)));

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

    @Test
    void inquiriesWithUserNotFound() {
        Long productId = 1L;

        int page = 1;

        Pageable pageable = PageRequest.of(page - 1, 8, Sort.by("createAt").descending());

        Page<Inquiry> pageableInquiries = new PageImpl<>(List.of(Inquiry.fake(productId)));

        UserName userName = new UserName("xxx");

        given(userRepository.findByUserName(userName))
                .willThrow(UserNotFound.class);

        given(inquiryRepository.findAllByProductId(productId, pageable))
                .willReturn(pageableInquiries);

        assertThrows(UserNotFound.class, () -> getInquiresService.inquiries(productId, userName, pageable));
    }
}
