package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.dtos.UserDto;
import com.junhyeong.shoppingmall.models.vo.Cart;
import com.junhyeong.shoppingmall.models.vo.UserName;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "PERSON")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private UserName userName;

    @Column(name = "password")
    private String encodedPassword;

    private String name;

    private String phoneNumber;

    @Embedded
    private Cart cart;

    @CreationTimestamp
    private LocalDateTime createAt;

    public User() {
    }

    public User(Long id, UserName userName,
                String name, String phoneNumber) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.cart = new Cart("{\"items\":[]}");
    }

    public User(UserName userName, String name, String phoneNumber) {
        this.userName = userName;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.cart = new Cart("{\"items\":[]}");
    }

    public User(UserName userName, String name) {
        this.userName = userName;
        this.name = name;
        this.cart = new Cart("{\"items\":[]}");
    }

    public Long id() {
        return id;
    }

    public UserName userName() {
        return userName;
    }

    public String name() {
        return name;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public Cart cart() {
        return cart;
    }

    public static User fake(UserName userName) {
        return new User(userName, "배준형", "01012341234");
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.encodedPassword = passwordEncoder.encode(password);
    }

    public boolean authenticate(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    public UserDto toDto() {
        boolean isSnsUser = encodedPassword == null;
        return new UserDto(id, userName.value(), name, phoneNumber, isSnsUser);
    }

    public void updateCart(Cart cart) {
        this.cart = cart;
    }
}
