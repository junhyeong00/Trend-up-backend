package com.junhyeong.shoppingmall.services.inquiry;

import com.junhyeong.shoppingmall.dtos.UpdateInquiryRequest;
import com.junhyeong.shoppingmall.exceptions.InquiryNotFound;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.inquiry.Inquiry;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.InquiryRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UpdateInquiryService {
    private final UserRepository userRepository;
    private final InquiryRepository inquiryRepository;

    public UpdateInquiryService(UserRepository userRepository,
                                InquiryRepository inquiryRepository) {
        this.userRepository = userRepository;
        this.inquiryRepository = inquiryRepository;
    }

    public void update(UserName userName, UpdateInquiryRequest updateInquiryRequest) {
        Inquiry inquiry = inquiryRepository.findById(updateInquiryRequest.getInquiryId())
                .orElseThrow(InquiryNotFound::new);

        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        inquiry.checkWriterAuthority(user.id());

        inquiry.update(updateInquiryRequest.getTitle(),
                updateInquiryRequest.getContent(),
                updateInquiryRequest.getSecret());
    }
}
