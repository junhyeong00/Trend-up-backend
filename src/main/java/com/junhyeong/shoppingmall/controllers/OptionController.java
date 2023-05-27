package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.OptionDto;
import com.junhyeong.shoppingmall.dtos.OptionsDto;
import com.junhyeong.shoppingmall.models.option.Option;
import com.junhyeong.shoppingmall.services.option.GetOptionsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
public class OptionController {
    private final GetOptionsService getOptionsService;

    public OptionController(GetOptionsService getOptionsService) {
        this.getOptionsService = getOptionsService;
    }

    @GetMapping("{productId}/options")
    public OptionsDto options(
            @PathVariable("productId") Long productId
    ) {
        List<Option> options = getOptionsService.options(productId);

        List<OptionDto> optionDtos = options.stream()
                .map(Option::toDto).toList();

        return new OptionsDto(optionDtos);
    }
}
