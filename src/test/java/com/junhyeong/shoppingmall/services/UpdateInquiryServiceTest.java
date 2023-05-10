package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.UpdateInquiryRequest;
import com.junhyeong.shoppingmall.exceptions.InquiryNotFound;
import com.junhyeong.shoppingmall.exceptions.IsNotWriter;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.inquiry.Inquiry;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.InquiryRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.services.inquiry.UpdateInquiryService;
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

        UpdateInquiryRequest updateInquiryRequest = UpdateInquiryRequest.fake(inquiryId);

        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(inquiry));

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        updateInquiryService.update(userName, updateInquiryRequest);

        verify(inquiryRepository).findById(inquiryId);
        verify(userRepository).findByUserName(userName);
    }

    @Test
    void updateFailedWithIsNotWriter() {
        UserName userName = new UserName("test123");

        User user = User.fake(userName);

        Long inquiryId = 1L;

        Long otherUserId = 2L;

        Inquiry inquiry = Inquiry.fake(inquiryId, otherUserId);

        UpdateInquiryRequest updateInquiryRequest = UpdateInquiryRequest.fake(inquiryId);

        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(inquiry));

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        assertThrows(IsNotWriter.class, () -> {
            updateInquiryService.update(userName, updateInquiryRequest);
        });
    }

    @Test
    void updateFailedWithInquiryNotFound() {
        UserName userName = new UserName("test123");

        User user = User.fake(userName);

        Long inquiryId = 999L;

        UpdateInquiryRequest updateInquiryRequest = UpdateInquiryRequest.fake(inquiryId);

        given(inquiryRepository.findById(inquiryId))
                .willThrow(InquiryNotFound.class);

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        assertThrows(InquiryNotFound.class, () -> {
            updateInquiryService.update(userName, updateInquiryRequest);
        });
    }

    @Test
    void updateFailedWithWriterNotFound() {
        UserName userName = new UserName("test123");

        Long inquiryId = 1L;

        Long invalidUserId = 999L;

        Inquiry inquiry = Inquiry.fake(inquiryId, invalidUserId);

        UpdateInquiryRequest updateInquiryRequest = UpdateInquiryRequest.fake(inquiryId);

        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(inquiry));

        given(userRepository.findByUserName(userName))
                .willThrow(UserNotFound.class);

        assertThrows(UserNotFound.class, () -> {
            updateInquiryService.update(userName, updateInquiryRequest);
        });
    }
}
