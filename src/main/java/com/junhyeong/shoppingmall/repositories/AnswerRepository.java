package com.junhyeong.shoppingmall.repositories;

import com.junhyeong.shoppingmall.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    boolean existsByInquiryId(Long inquiryId);
}
