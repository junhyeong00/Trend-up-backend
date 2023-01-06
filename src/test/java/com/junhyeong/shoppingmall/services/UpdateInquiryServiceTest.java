package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.exceptions.isNotWriter;
import com.junhyeong.shoppingmall.models.Inquiry;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.InquiryRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UpdateInquiryServiceTest {
    private UserRepository userRepository;
    private InquiryRepository inquiryRepository;
    private UpdateInquiryService updateInquiryService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        inquiryRepository = mock(InquiryRepository.class);
        updateInquiryService = new UpdateInquiryService(userRepository, inquiryRepository);
    }

    @Test
    void update() {
        UserName userName = new UserName("test123");

        User user = User.fake(userName);

        Long inquiryId = 1L;

        Inquiry inquiry = Inquiry.fake(inquiryId, user.id());

        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(inquiry));

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        updateInquiryService.update(userName, inquiryId, "색상 문의", "빨간색은 없나요?", false);

        verify(inquiryRepository).findById(inquiryId);
        verify(userRepository).findByUserName(userName);
    }

    @Test
    void updateFailed() {
        UserName userName = new UserName("test123");

        User user = User.fake(userName);

        Long inquiryId = 1L;

        Long otherUserId = 2L;

        Inquiry inquiry = Inquiry.fake(inquiryId, otherUserId);

        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(inquiry));

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        assertThrows(isNotWriter.class, () -> {
            updateInquiryService.update(userName, inquiryId, "색상 문의", "빨간색은 없나요?", false);
        });
    }
}
