package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.InquiriesDto;
import com.junhyeong.shoppingmall.dtos.InquiryDto;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.Inquiry;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
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

    public GetInquiresService(UserRepository userRepository,
                              InquiryRepository inquiryRepository) {
        this.userRepository = userRepository;
        this.inquiryRepository = inquiryRepository;
    }

    public InquiriesDto inquiries(Long productId, UserName userName, Pageable pageable) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        Page<Inquiry> inquiries = inquiryRepository.findAllByProductId(productId, pageable);

        int totalPageCount = inquiries.getTotalPages();

        List<InquiryDto> inquiryDtos = toDto(user, inquiries);

        return new InquiriesDto(inquiryDtos, totalPageCount);
    }

    private List<InquiryDto> toDto(User user, Page<Inquiry> inquiries) {
        List<InquiryDto> inquiryDtos = inquiries.stream().map(inquiry -> {
            User writer = userRepository.findById(inquiry.userId())
                    .orElseThrow(UserNotFound::new);
            return inquiry.toDto(user.id(), writer.userName(), "미답변");
        }).toList();
        return inquiryDtos;
    }
}
