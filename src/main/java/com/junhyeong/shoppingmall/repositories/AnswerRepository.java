package com.junhyeong.shoppingmall.repositories;

import com.junhyeong.shoppingmall.models.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    boolean existsByInquiryId(Long inquiryId);

    Answer findByInquiryId(Long inquiryId);
}
