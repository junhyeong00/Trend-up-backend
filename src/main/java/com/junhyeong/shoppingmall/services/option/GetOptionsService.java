package com.junhyeong.shoppingmall.services.option;

import com.junhyeong.shoppingmall.models.option.Option;
import com.junhyeong.shoppingmall.repositories.OptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetOptionsService {
    private final OptionRepository optionRepository;

    public GetOptionsService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Transactional(readOnly = true)
    public List<Option> options(Long productId) {
        return optionRepository.findByProductId(productId);
    }
}
