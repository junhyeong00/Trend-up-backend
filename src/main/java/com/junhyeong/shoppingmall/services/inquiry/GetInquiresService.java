package com.junhyeong.shoppingmall.services.inquiry;

import com.junhyeong.shoppingmall.dtos.InquiriesDto;
import com.junhyeong.shoppingmall.dtos.InquiryDto;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.answer.Answer;
import com.junhyeong.shoppingmall.models.inquiry.Inquiry;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.AnswerRepository;
import com.junhyeong.shoppingmall.repositories.InquiryRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetInquiresService {
    private final UserRepository userRepository;
    private final InquiryRepository inquiryRepository;
    private final AnswerRepository answerRepository;

    public GetInquiresService(UserRepository userRepository,
                              InquiryRepository inquiryRepository, AnswerRepository answerRepository) {
        this.userRepository = userRepository;
        this.inquiryRepository = inquiryRepository;
        this.answerRepository = answerRepository;
    }

    @Transactional(readOnly = true)
    public InquiriesDto inquiries(Long productId, UserName userName, Pageable pageable) {
        Page<Inquiry> inquiries = inquiryRepository.findAllByProductId(productId, pageable);

        int totalPageCount = inquiries.getTotalPages();

        List<InquiryDto> inquiryDtos = toDto(userName, inquiries);

        return new InquiriesDto(inquiryDtos, totalPageCount);
    }

    private List<InquiryDto> toDto(UserName userName, Page<Inquiry> inquiries) {
        Long userId = null;

        if (userName != null) {
            User user = userRepository.findByUserName(userName)
                    .orElseThrow(UserNotFound::new);

            userId = user.id();
        }

        Long viewerId = userId;

        List<InquiryDto> inquiryDtos = inquiries.stream().map(inquiry -> {
            User writer = userRepository.findById(inquiry.userId())
                    .orElseThrow(UserNotFound::new);

            boolean answerStatus = answerRepository.existsByInquiryId(inquiry.id());

            if (answerStatus) {
                Answer answer = answerRepository.findByInquiryId(inquiry.id());

                return inquiry.toDto(viewerId, writer.userName(), answerStatus, answer.comment(), answer.createdAt());
            }

            return inquiry.toDto(viewerId, writer.userName(), answerStatus);
        }).toList();
        return inquiryDtos;
    }
}
