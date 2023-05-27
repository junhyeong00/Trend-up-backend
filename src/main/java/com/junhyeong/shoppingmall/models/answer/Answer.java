package com.junhyeong.shoppingmall.models.answer;

import com.junhyeong.shoppingmall.dtos.AnswerResultDto;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    private Long inquiryId;

    private String comment;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Answer() {
    }

    public Answer(Long id, Long inquiryId, String comment) {
        this.id = id;
        this.inquiryId = inquiryId;
        this.comment = comment;
    }

    public Answer(Long inquiryId, String comment) {
        this.inquiryId = inquiryId;
        this.comment = comment;
    }

    public static Answer fake(Long answerId) {
        Long inquiryId = 1L;
        return new Answer(answerId, inquiryId, "해당 상품은 일주일 내로 재입고 될 예정입니다");
    }

    public Long id() {
        return id;
    }

    public Long inquiryId() {
        return inquiryId;
    }

    public String comment() {
        return comment;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public AnswerResultDto toResultDto() {
        return new AnswerResultDto(id);
    }
}
