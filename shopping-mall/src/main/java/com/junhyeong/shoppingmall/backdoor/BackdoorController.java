package com.junhyeong.shoppingmall.backdoor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("backdoor")
public class BackdoorController {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public BackdoorController(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("setup-products")
    public String setupProducts() {
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.execute("DELETE FROM product");
        jdbcTemplate.execute("DELETE FROM option");

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                1, "남성 패션", "가디건", "가볍다", 50000L, null, now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id, stock_quantity)" +
                "VALUES(1, '기본', 0, 1, 4)"
        );

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id, stock_quantity)" +
                "VALUES(2, '두툼한 가디건', 1000, 1, 3)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                2, "남성 패션", "신발", "가볍다", 40000L, null, now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id, stock_quantity)" +
                "VALUES(3, '사이즈 250', 0, 2, 3)"
        );

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id, stock_quantity)" +
                "VALUES(4, '사이즈 260', 0, 2, 4)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                3, "화장품/미용", "수분크림", "촉촉하다", 20000L, null, now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id, stock_quantity)" +
                "VALUES(5, '촉촉', 0, 3, 8)"
        );
        return "OK";
    }

    @GetMapping("setup-user")
    public String setupUser() {
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.execute("DELETE FROM person");

        jdbcTemplate.update("INSERT INTO " +
                        "person(id, user_name, password, name, phone_number, create_at)" +
                        "VALUES(1, ?, ?, ?, 01012345678, ?)",
                "test123", passwordEncoder.encode("test123"), "배준형", now);

        return "OK";
    }
}
