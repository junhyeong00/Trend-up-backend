package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.dtos.InquiriesDto;
import com.junhyeong.shoppingmall.dtos.InquiryDto;
import com.junhyeong.shoppingmall.exceptions.ProductNotFound;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.Inquiry;
import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.models.User;
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

    public GetInquiriesAdminService(InquiryRepository inquiryRepository, UserRepository userRepository,
                                    ProductRepository productRepository) {
        this.inquiryRepository = inquiryRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
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
            return inquiry.toAdminDto(writer.userName(), "미답변", product.name());
        }).toList();
        return inquiryDtos;
    }
}
