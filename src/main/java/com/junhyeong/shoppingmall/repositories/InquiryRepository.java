package com.junhyeong.shoppingmall.repositories;

import com.junhyeong.shoppingmall.models.Inquiry;
import com.junhyeong.shoppingmall.models.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
