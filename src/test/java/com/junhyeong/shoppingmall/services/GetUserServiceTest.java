package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.services.user.GetUserService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GetUserServiceTest {
    @Test
    void user() {
        UserRepository userRepository = mock(UserRepository.class);
        GetUserService getUserService = new GetUserService(userRepository);

        UserName userName = new UserName("test123");
        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(User.fake(userName)));

        User user = getUserService.find(userName);

        assertThat(user).isNotNull();
        verify(userRepository).findByUserName(userName);
    }
}
