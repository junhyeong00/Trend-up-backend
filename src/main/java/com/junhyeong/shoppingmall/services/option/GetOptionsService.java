package com.junhyeong.shoppingmall.services.option;

import com.junhyeong.shoppingmall.models.option.Option;
import com.junhyeong.shoppingmall.repositories.OptionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GetOptionsService {
    private final OptionRepository optionRepository;

    public GetOptionsService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<Option> options(Long productId) {
        return optionRepository.findByProductId(productId);
    }
}
