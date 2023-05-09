package com.junhyeong.shoppingmall.controllers;

import com.junhyeong.shoppingmall.dtos.CartDto;
import com.junhyeong.shoppingmall.dtos.RegistrationRequestDto;
import com.junhyeong.shoppingmall.dtos.RegistrationResultDto;
import com.junhyeong.shoppingmall.dtos.UpdateCartDto;
import com.junhyeong.shoppingmall.dtos.UserDto;
import com.junhyeong.shoppingmall.exceptions.RegisterFailed;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.Cart;
import com.junhyeong.shoppingmall.models.vo.UserName;
import com.junhyeong.shoppingmall.services.user.CreateUserService;
import com.junhyeong.shoppingmall.services.cart.GetCartService;
import com.junhyeong.shoppingmall.services.user.GetUserService;
import com.junhyeong.shoppingmall.services.cart.UpdateCartService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final GetUserService getUserService;
    private final GetCartService getCartService;
    private final UpdateCartService updateCartService;
    private final CreateUserService createUserService;

    public UserController(GetUserService getUserService,
                          GetCartService getCartService,
                          UpdateCartService updateCartService,
                          CreateUserService createUserService) {
        this.getUserService = getUserService;
        this.getCartService = getCartService;
        this.updateCartService = updateCartService;
        this.createUserService = createUserService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private RegistrationResultDto register(
            @Valid @RequestBody RegistrationRequestDto registrationRequestDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();

            throw new RegisterFailed(errorMessages);
        }

        UserName userName = new UserName(registrationRequestDto.getUserName());

        User user = createUserService.register(
                registrationRequestDto.getName(),
                userName,
                registrationRequestDto.getPassword(),
                registrationRequestDto.getConfirmPassword(),
                registrationRequestDto.getPhoneNumber()
        );

        return new RegistrationResultDto(user.name());
    }

    @GetMapping("me")
    public UserDto user(
            @RequestAttribute("userName") UserName userName
    ) {
        User user = getUserService.user(userName);
        return user.toDto();
    }

    @GetMapping("cart")
    public CartDto cart(
            @RequestAttribute("userName") UserName userName
    ) {
        return getCartService.cart(userName);
    }

    @PatchMapping("cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCart(
            @RequestAttribute("userName") UserName userName,
            @RequestBody UpdateCartDto updateCartDto
    ) {
        Cart cart = new Cart(updateCartDto.getItems());

        updateCartService.updateCart(userName, cart);
    }

    @ExceptionHandler(RegisterFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String signUpFailed(RegisterFailed e) {
        return e.errorMessages().get(0);
    }
}
