package com.junhyeong.shoppingmall.services.inquiry;

import com.junhyeong.shoppingmall.dtos.InquiriesDto;
import com.junhyeong.shoppingmall.dtos.InquiryDto;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.Answer;
import com.junhyeong.shoppingmall.models.inquiry.Inquiry;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.AnswerRepository;
import com.junhyeong.shoppingmall.repositories.InquiryRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
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

    public InquiriesDto inquiries(Long productId, UserName userName, Pageable pageable) {


        Page<Inquiry> inquiries = inquiryRepository.findAllByProductId(productId, pageable);

        int totalPageCount = inquiries.getTotalPages();

        if (userName != null) {
            User user = userRepository.findByUserName(userName)
                    .orElseThrow(UserNotFound::new);

            List<InquiryDto> inquiryDtos = toDto(user, inquiries);

            return new InquiriesDto(inquiryDtos, totalPageCount);
        }

        List<InquiryDto> inquiryDtos = toDto(inquiries);

        return new InquiriesDto(inquiryDtos, totalPageCount);
    }

    private List<InquiryDto> toDto(User user, Page<Inquiry> inquiries) {
        List<InquiryDto> inquiryDtos = inquiries.stream().map(inquiry -> {
            User writer = userRepository.findById(inquiry.userId())
                    .orElseThrow(UserNotFound::new);

            boolean answerStatus = answerRepository.existsByInquiryId(inquiry.id());

            if (answerStatus) {
                Answer answer = answerRepository.findByInquiryId(inquiry.id());

                return inquiry.toDto(user.id(), writer.userName(), answerStatus, answer.comment(), answer.createdAt());
            }

            return inquiry.toDto(user.id(), writer.userName(), answerStatus);
        }).toList();
        return inquiryDtos;
    }

    private List<InquiryDto> toDto(Page<Inquiry> inquiries) {
        List<InquiryDto> inquiryDtos = inquiries.stream().map(inquiry -> {
            User writer = userRepository.findById(inquiry.userId())
                    .orElseThrow(UserNotFound::new);

            boolean answerStatus = answerRepository.existsByInquiryId(inquiry.id());

            if (answerStatus) {
                Answer answer = answerRepository.findByInquiryId(inquiry.id());

                return inquiry.toDto(null, writer.userName(), answerStatus, answer.comment(), answer.createdAt());
            }

            return inquiry.toDto(null, writer.userName(), answerStatus);
        }).toList();
        return inquiryDtos;
    }
}
