package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.dtos.InquiriesDto;
import com.junhyeong.shoppingmall.dtos.InquiryDto;
import com.junhyeong.shoppingmall.exceptions.ProductNotFound;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.inquiry.Inquiry;
import com.junhyeong.shoppingmall.models.product.Product;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.repositories.AnswerRepository;
import com.junhyeong.shoppingmall.repositories.InquiryRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GetInquiriesAdminService {
    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AnswerRepository answerRepository;

    public GetInquiriesAdminService(InquiryRepository inquiryRepository, UserRepository userRepository,
                                    ProductRepository productRepository, AnswerRepository answerRepository) {
        this.inquiryRepository = inquiryRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.answerRepository = answerRepository;
    }

    public InquiriesDto inquiries(Pageable pageable) {
        Page<Inquiry> inquiries = inquiryRepository.findAll(pageable);

        int totalPageCount = inquiries.getTotalPages();

        List<InquiryDto> inquiryDtos = toDto(inquiries);

        return new InquiriesDto(inquiryDtos, totalPageCount);
    }

    private List<InquiryDto> toDto(Page<Inquiry> inquiries) {
        List<InquiryDto> inquiryDtos = inquiries.stream().map(inquiry -> {
            User writer = userRepository.findById(inquiry.userId())
                    .orElseThrow(UserNotFound::new);

            Product product = productRepository.findById(inquiry.productId())
                    .orElseThrow(ProductNotFound::new);

            boolean answerStatus = answerRepository.existsByInquiryId(inquiry.id());

            return inquiry.toAdminDto(writer.userName(), answerStatus, product.name());
        }).toList();
        return inquiryDtos;
    }
}
