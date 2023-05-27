package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.exceptions.InquiryNotFound;
import com.junhyeong.shoppingmall.exceptions.IsNotWriter;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.inquiry.Inquiry;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.InquiryRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.services.inquiry.DeleteInquiryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DeleteInquiryServiceTest {
    private UserRepository userRepository;
    private InquiryRepository inquiryRepository;
    private DeleteInquiryService deleteInquiryService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        inquiryRepository = mock(InquiryRepository.class);
        deleteInquiryService = new DeleteInquiryService(userRepository, inquiryRepository);
    }

    @Test
    void delete() {
        UserName userName = new UserName("test123");

        User user = User.fake(userName);

        Long inquiryId = 1L;

        Inquiry inquiry = Inquiry.fake(inquiryId, user.id());

        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(inquiry));

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        deleteInquiryService.delete(userName, inquiryId);

        verify(inquiryRepository).findById(inquiryId);
        verify(userRepository).findByUserName(userName);

        verify(inquiryRepository).delete(inquiry);
    }

    @Test
    void deleteFailedWithIsNotWriter() {
        UserName userName = new UserName("test123");

        User user = User.fake(userName);

        Long inquiryId = 1L;

        Long otherUserId = 2L;

        Inquiry inquiry = Inquiry.fake(inquiryId,otherUserId);

        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(inquiry));

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        assertThrows(IsNotWriter.class, () -> {
            deleteInquiryService.delete(userName, inquiryId);
        });
    }

    @Test
    void deleteFailedWithUserNotFound() {
        UserName userName = new UserName("xxx");

        User user = User.fake(userName);

        Long inquiryId = 1L;

        Inquiry inquiry = Inquiry.fake(inquiryId,user.id());

        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(inquiry));

        given(userRepository.findByUserName(userName))
                .willThrow(UserNotFound.class);

        assertThrows(UserNotFound.class, () -> {
            deleteInquiryService.delete(userName, inquiryId);
        });
    }

    @Test
    void deleteFailedWithInquiryNotFound() {
        UserName userName = new UserName("xxx");

        Long inquiryId = 999L;

        given(inquiryRepository.findById(inquiryId))
                .willThrow(InquiryNotFound.class);

        assertThrows(InquiryNotFound.class, () -> {
            deleteInquiryService.delete(userName, inquiryId);
        });
    }
}
