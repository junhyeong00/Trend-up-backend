package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.exceptions.DeleteInquiryFailed;
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
    void deleteFailed() {
        UserName userName = new UserName("test123");

        User user = User.fake(userName);

        Long inquiryId = 1L;

        Long otherUserId = 2L;

        Inquiry inquiry = Inquiry.fake(inquiryId,otherUserId);

        given(inquiryRepository.findById(inquiryId))
                .willReturn(Optional.of(inquiry));

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        assertThrows(DeleteInquiryFailed.class, () -> {
            deleteInquiryService.delete(userName, inquiryId);
        });
    }
}
