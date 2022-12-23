package com.junhyeong.shoppingmall.dtos;

import java.util.List;

public class OptionsDto {
    private List<OptionDto> options;

    public OptionsDto(List<OptionDto> options) {
        this.options = options;
    }

    public List<OptionDto> getOptions() {
        return options;
    }
}
