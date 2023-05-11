package com.junhyeong.shoppingmall.models.inquiry;

import com.junhyeong.shoppingmall.dtos.InquiryDto;
import com.junhyeong.shoppingmall.dtos.CreateInquiryResultDto;
import com.junhyeong.shoppingmall.exceptions.IsNotWriter;
import com.junhyeong.shoppingmall.models.user.UserName;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Inquiry {
    @Id
    @GeneratedValue
    private Long id;

    private Long productId;

    private Long userId;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    private Boolean isSecret;

    @CreationTimestamp
    private LocalDateTime createAt;

    public Inquiry() {
    }

    public Inquiry(Long id, Long productId, Long userId, Title title, Content content, Boolean isSecret) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
    }

    public Inquiry(Long productId, Long userId, Title title, Content content, Boolean isSecret) {
        this.productId = productId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
    }

    public static Inquiry fake(Long inquiryId) {
        Long productId = 1L;
        Long userId = 1L;
        return new Inquiry(inquiryId, productId, userId,
                new Title("재입고 질문"), new Content("재입고 언제 될까요?"), false);
    }

    public static Inquiry fake(Long inquiryId, Long userId) {
        Long productId = 1L;
        return new Inquiry(inquiryId, productId, userId,
                new Title("재입고 질문"), new Content("재입고 언제 될까요?"), false);
    }

    public Long id() {
        return id;
    }

    public Long productId() {
        return productId;
    }

    public Long userId() {
        return userId;
    }

    public Title title() {
        return title;
    }

    public Content content() {
        return content;
    }

    public Boolean isSecret() {
        return isSecret;
    }

    public LocalDateTime createAt() {
        return createAt;
    }

    public CreateInquiryResultDto toResultDto() {
        return new CreateInquiryResultDto(id);
    }

    public InquiryDto toDto(Long userId, UserName userName, boolean answerStatus,
                            String comment, LocalDateTime answerCreateAt) {
        Boolean isMine = isWriter(userId);

        if (isSecret && !isMine) {
            return changeToSecret(answerStatus, isMine);
        }

        return new InquiryDto(this.id, userName.value(), title.value(), content.value(), answerStatus,
                createAt, isSecret, isMine, comment, answerCreateAt);
    }

    public InquiryDto toDto(Long userId, UserName userName, boolean answerStatus) {
        Boolean isMine = isWriter(userId);

        if (isSecret && !isMine) {
            return changeToSecret(answerStatus, isMine);
        }

        return new InquiryDto(this.id, userName.value(), title.value(), content.value(), answerStatus, createAt, isSecret, isMine);
    }

    public Boolean isWriter(Long userId) {
        return Objects.equals(this.userId, userId);
    }

    public InquiryDto changeToSecret(boolean answerStatus, Boolean isMine) {
        String title = "비밀글입니다";
        String content = "접근 권한이 없습니다";
        String writer = "****";

        return new InquiryDto(this.id, writer, title, content, answerStatus, createAt, isSecret, isMine);
    }

    public void checkWriterAuthority(Long userId) {
        if (!Objects.equals(this.userId, userId)) {
            throw new IsNotWriter();
        }
    }

    public void update(Title title, Content content, Boolean isSecret) {
        this.title = title;
        this.content = content;
        this.isSecret = isSecret;
    }

    public InquiryDto toAdminDto(UserName userName, boolean answerStatus, String productName) {
        return new InquiryDto(this.id, userName.value(), productName,
                title.value(), content.value(), answerStatus, createAt, isSecret);
    }
}
