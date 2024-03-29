package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.CreateInquiryRequest;
import com.junhyeong.shoppingmall.dtos.UpdateInquiryRequest;
import com.junhyeong.shoppingmall.dtos.InquiriesDto;
import com.junhyeong.shoppingmall.dtos.CreateInquiryRequestDto;
import com.junhyeong.shoppingmall.dtos.CreateInquiryResultDto;
import com.junhyeong.shoppingmall.dtos.UpdateInquiryDto;
import com.junhyeong.shoppingmall.exceptions.CreateInquiryFailed;
import com.junhyeong.shoppingmall.exceptions.InquiryNotFound;
import com.junhyeong.shoppingmall.exceptions.IsNotWriter;
import com.junhyeong.shoppingmall.exceptions.ProductNotFound;
import com.junhyeong.shoppingmall.exceptions.UpdateInquiryFailed;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.services.inquiry.CreateInquiryService;
import com.junhyeong.shoppingmall.services.inquiry.DeleteInquiryService;
import com.junhyeong.shoppingmall.services.inquiry.GetInquiresService;
import com.junhyeong.shoppingmall.services.inquiry.UpdateInquiryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class InquiryController {
    private final CreateInquiryService createInquiryService;
    private final GetInquiresService getInquiresService;
    private final DeleteInquiryService deleteInquiryService;
    private final UpdateInquiryService updateInquiryService;

    public InquiryController(CreateInquiryService createInquiryService,
                             GetInquiresService getInquiresService,
                             DeleteInquiryService deleteInquiryService,
                             UpdateInquiryService updateInquiryService) {
        this.createInquiryService = createInquiryService;
        this.getInquiresService = getInquiresService;
        this.deleteInquiryService = deleteInquiryService;
        this.updateInquiryService = updateInquiryService;
    }

    @GetMapping("products/{productId}/inquiries")
    public InquiriesDto inquiries(
            @PathVariable("productId") Long productId,
            @RequestAttribute(name = "userName", required = false) UserName userName,
            @PageableDefault(size = 8, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return getInquiresService.inquiries(productId, userName, pageable);
    }

    @PostMapping("inquiries")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateInquiryResultDto write(
            @RequestAttribute("userName") UserName userName,
            @Valid @RequestBody CreateInquiryRequestDto createInquiryRequestDto
    ) {
        try {
            CreateInquiryRequest createInquiryRequest = CreateInquiryRequest.of(createInquiryRequestDto);

            return createInquiryService.write(userName, createInquiryRequest);
        } catch (ProductNotFound | UserNotFound e) {
            throw e;
        } catch (Exception e) {
            throw new CreateInquiryFailed(e.getMessage());
        }
    }

    @DeleteMapping("inquiries/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestAttribute("userName") UserName userName,
            @PathVariable("id") Long inquiryId
    ) {
        deleteInquiryService.delete(userName, inquiryId);
    }

    @PatchMapping("inquiries/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @Valid @RequestBody UpdateInquiryDto updateInquiryDto,
            @RequestAttribute("userName") UserName userName,
            @PathVariable("id") Long inquiryId
    ) {
        try {
            UpdateInquiryRequest updateInquiryRequest = UpdateInquiryRequest.of(updateInquiryDto, inquiryId);

            updateInquiryService.update(userName, updateInquiryRequest);
        } catch (InquiryNotFound | UserNotFound e) {
            throw e;
        } catch (IsNotWriter isNotWriter) {
            throw new IsNotWriter();
        } catch (Exception e) {
            throw new UpdateInquiryFailed(e.getMessage());
        }
    }

    @ExceptionHandler(InquiryNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String inquiryNotFound(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(UserNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFound(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(ProductNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productNotFound(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(IsNotWriter.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String isNotWriter(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(UpdateInquiryFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String updateInquiryFailed(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(CreateInquiryFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String createInquiryFailed(Exception e) {
        return e.getMessage();
    }
}
