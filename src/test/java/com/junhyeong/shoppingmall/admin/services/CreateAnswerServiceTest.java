package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.exceptions.AnswerWriteFailed;
import com.junhyeong.shoppingmall.models.Answer;
import com.junhyeong.shoppingmall.repositories.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateAnswerServiceTest {
    private AnswerRepository answerRepository;
    private CreateAnswerService createAnswerService;

    @BeforeEach
    void setup() {
        answerRepository = mock(AnswerRepository.class);
        createAnswerService = new CreateAnswerService(answerRepository);
    }

    @Test
    void write() {
        Long inquiryId = 1L;

        given(answerRepository.existsByInquiryId(inquiryId)).willReturn(false);

        createAnswerService.write(inquiryId, "해당 상품은 일주일 내로 재입고 될 예정입니다");

        verify(answerRepository).existsByInquiryId(inquiryId);
        verify(answerRepository).save(any(Answer.class));
    }

    @Test
    void writeWithExistsAnswer() {
        Long inquiryId = 1L;

        given(answerRepository.existsByInquiryId(inquiryId)).willReturn(true);

        assertThrows(AnswerWriteFailed.class, () -> {
            createAnswerService.write(inquiryId, "해당 상품은 일주일 내로 재입고 될 예정입니다");
        });
    }
}
