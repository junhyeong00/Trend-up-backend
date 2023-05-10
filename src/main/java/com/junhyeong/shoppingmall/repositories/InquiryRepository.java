package com.junhyeong.shoppingmall.repositories;

import com.junhyeong.shoppingmall.models.inquiry.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    Page<Inquiry> findAllByProductId(Long productId, Pageable pageable);
}
