package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.exceptions.DeleteInquiryFailed;
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
public class DeleteInquiryService {
    private final UserRepository userRepository;
    private final InquiryRepository inquiryRepository;


    public DeleteInquiryService(UserRepository userRepository,
                                InquiryRepository inquiryRepository) {
        this.userRepository = userRepository;
        this.inquiryRepository = inquiryRepository;
    }

    public void delete(UserName userName, Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(InquiryNotFound::new);

        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        if (!inquiry.isWriter(user.id())) {
         throw new DeleteInquiryFailed();
        }

        inquiryRepository.delete(inquiry);
    }
}
