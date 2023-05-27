package com.junhyeong.shoppingmall.services.inquiry;

import com.junhyeong.shoppingmall.dtos.CreateInquiryRequest;
import com.junhyeong.shoppingmall.dtos.CreateInquiryResultDto;
import com.junhyeong.shoppingmall.exceptions.ProductNotFound;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.inquiry.Inquiry;
import com.junhyeong.shoppingmall.models.product.Product;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.InquiryRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
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

    @Transactional
    public CreateInquiryResultDto write(UserName userName, CreateInquiryRequest createInquiryRequest) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        Product product = productRepository.findById(createInquiryRequest.getProductId())
                .orElseThrow(ProductNotFound::new);

        Inquiry inquiry = new Inquiry(
                product.id(),
                user.id(),
                createInquiryRequest.getTitle(),
                createInquiryRequest.getContent(),
                createInquiryRequest.getSecret());

        inquiryRepository.save(inquiry);
        return inquiry.toResultDto();
    }
}
