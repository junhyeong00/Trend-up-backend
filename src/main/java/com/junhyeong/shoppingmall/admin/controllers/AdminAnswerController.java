package com.junhyeong.shoppingmall.admin.controllers;

import com.junhyeong.shoppingmall.admin.services.CreateAnswerService;
import com.junhyeong.shoppingmall.dtos.AnswerRequestDto;
import com.junhyeong.shoppingmall.dtos.AnswerResultDto;
import com.junhyeong.shoppingmall.dtos.AnswerWriteErrorDto;
import com.junhyeong.shoppingmall.exceptions.AnswerWriteFailed;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/answer")
public class AdminAnswerController {
    private final CreateAnswerService createAnswerService;

    public AdminAnswerController(CreateAnswerService createAnswerService) {
        this.createAnswerService = createAnswerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerResultDto write(
            @Validated @RequestBody AnswerRequestDto answerRequestDto
    ) {
        return createAnswerService.write(
                answerRequestDto.getInquiryId(),
                answerRequestDto.getComment());
    }

    @ExceptionHandler(AnswerWriteFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AnswerWriteErrorDto writeFailed(AnswerWriteFailed e) {
        return new AnswerWriteErrorDto(e.getMessage());
    }
}
