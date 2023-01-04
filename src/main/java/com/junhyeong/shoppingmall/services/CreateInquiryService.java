package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.InquiryResultDto;
import com.junhyeong.shoppingmall.exceptions.ProductNotFound;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.Inquiry;
import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.repositories.InquiryRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CreateInquiryService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final InquiryRepository inquiryRepository;

    public CreateInquiryService(UserRepository userRepository,
                                ProductRepository productRepository,
                                InquiryRepository inquiryRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.inquiryRepository = inquiryRepository;
    }

    public InquiryResultDto write(UserName userName, Long productId, String title, String content, Boolean isSecret) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFound::new);

        Inquiry inquiry = new Inquiry(product.id(), user.id(), title, content, isSecret);

        inquiryRepository.save(inquiry);
        return inquiry.toResultDto();
    }
}
