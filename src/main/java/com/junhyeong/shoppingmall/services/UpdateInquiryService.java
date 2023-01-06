package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.exceptions.isNotWriter;
import com.junhyeong.shoppingmall.exceptions.InquiryNotFound;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.Inquiry;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
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

    public void update(UserName userName, Long inquiryId,
                       String title, String content, Boolean isSecret) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(InquiryNotFound::new);

        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        if (!inquiry.isWriter(user.id())) {
            throw new isNotWriter();
        }

        inquiry.update(title, content, isSecret);
    }
}
