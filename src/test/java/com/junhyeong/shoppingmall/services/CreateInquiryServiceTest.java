package com.junhyeong.shoppingmall.services;

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
import com.junhyeong.shoppingmall.services.inquiry.CreateInquiryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateInquiryServiceTest {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private InquiryRepository inquiryRepository;
    private CreateInquiryService createInquiryService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        productRepository = mock(ProductRepository.class);
        inquiryRepository = mock(InquiryRepository.class);
        createInquiryService = new CreateInquiryService(userRepository, productRepository, inquiryRepository);
    }

    @Test
    void write() {
        UserName userName = new UserName("test123");
        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        Long productId = 1L;

        CreateInquiryRequest createInquiryRequest = CreateInquiryRequest.fake(productId);

        given(productRepository.findById(productId))
                .willReturn(Optional.of(Product.fake(productId)));

        Long inquiryId = 1L;

        given(inquiryRepository.save(Inquiry.fake(inquiryId)))
                .willReturn(Inquiry.fake(inquiryId));

        CreateInquiryResultDto createInquiryResultDto =
                createInquiryService.write(userName, createInquiryRequest);

        assertThat(createInquiryResultDto).isNotNull();
    }

    @Test
    void writeFailedWithProductNotFound() {
        UserName userName = new UserName("test123");
        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        Long productId = 1L;

        given(productRepository.findById(productId))
                .willThrow(ProductNotFound.class);

        CreateInquiryRequest createInquiryRequest = CreateInquiryRequest.fake(productId);

        assertThrows(ProductNotFound.class, () -> {
            createInquiryService.write(userName, createInquiryRequest);
        });
    }

    @Test
    void writeFailedWithWriterNotFound() {
        UserName userName = new UserName("test123");
        given(userRepository.findByUserName(userName))
                .willThrow(UserNotFound.class);

        Long productId = 1L;

        CreateInquiryRequest createInquiryRequest = CreateInquiryRequest.fake(productId);

        given(productRepository.findById(productId))
                .willReturn(Optional.of(Product.fake(productId)));

        assertThrows(UserNotFound.class, () -> {
            createInquiryService.write(userName, createInquiryRequest);
        });
    }
}
