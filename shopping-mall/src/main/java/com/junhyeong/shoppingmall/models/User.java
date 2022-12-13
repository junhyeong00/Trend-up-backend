package com.junhyeong.shoppingmall.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
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
    }

    public User(UserName userName, String name, String phoneNumber) {
        this.userName = userName;
        this.name = name;
        this.phoneNumber = phoneNumber;
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

    public static User fake(UserName userName) {
        return new User(userName, "배준형", "01012345678");
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.encodedPassword = passwordEncoder.encode(password);
    }

    public boolean authenticate(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
