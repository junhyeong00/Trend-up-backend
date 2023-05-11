package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.dtos.AnswerResultDto;
import com.junhyeong.shoppingmall.exceptions.AnswerWriteFailed;
import com.junhyeong.shoppingmall.models.answer.Answer;
import com.junhyeong.shoppingmall.repositories.AnswerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CreateAnswerService {
    private final AnswerRepository answerRepository;

    public CreateAnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public AnswerResultDto write(Long inquiryId, String comment) {
        if (answerRepository.existsByInquiryId(inquiryId)) {
            throw new AnswerWriteFailed();
        }

        Answer answer = new Answer(inquiryId, comment);

        answerRepository.save(answer);

        return answer.toResultDto();
    }
}
