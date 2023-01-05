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
        jdbcTemplate.execute("DELETE FROM category");

        jdbcTemplate.update("INSERT INTO " +
                        "category(id, name)" +
                        "VALUES(?, ?)",
                1, "상의");

        jdbcTemplate.update("INSERT INTO " +
                        "category(id, name)" +
                        "VALUES(?, ?)",
                2, "하의");

        jdbcTemplate.update("INSERT INTO " +
                        "category(id, name)" +
                        "VALUES(?, ?)",
                3, "신발");

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                1, 1, "가디건", "가볍다", 50000L, "", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id, stock_quantity)" +
                "VALUES(1, '기본', 0, 1, 4)"
        );

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id, stock_quantity)" +
                "VALUES(2, '두툼한 가디건', 1000, 1, 3)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                2, 3, "신발", "가볍다", 40000L, "", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id, stock_quantity)" +
                "VALUES(3, '사이즈 250', 0, 2, 3)"
        );

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id, stock_quantity)" +
                "VALUES(4, '사이즈 260', 0, 2, 4)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                3, 2, "청바지", "파랗다", 20000L,
                "http://res.cloudinary.com/dywsfe5du/image/upload/v1671960484/wsvymh09izmwpt73vz99.jpg", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id, stock_quantity)" +
                "VALUES(5, '사이즈 28', 0, 3, 8)"
        );

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id, stock_quantity)" +
                "VALUES(6, '사이즈 30', 0, 3, 8)"
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

    @GetMapping("setup-reviews")
    public String setupReviews() {
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.execute("DELETE FROM review");

//        jdbcTemplate.update("INSERT INTO " +
//                        "review(id, user_id, order_id, order_product, rating, content, image, create_at, isDeleted)" +
//                        "VALUES(1, ?, ?, ?, 01012345678, ?)",
//                "test123", passwordEncoder.encode("test123"), "배준형", now);
        return "OK";
    }

    @GetMapping("setup-orders")
    public String setupOrders() {
        // TODO orders
        return "OK";
    }

    @GetMapping("setup-inquiries")
    public String setupInquiries() {
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.execute("DELETE FROM inquiry");

        jdbcTemplate.update("INSERT INTO " +
                        "inquiry(id, user_id, product_id,  title, content, create_at)" +
                        "VALUES(1, ?, ?, ?, ?, ?)",
                1L, 1L, "재입고 문의", "재입고 언제 되나요?",now);
        return "OK";
    }
}
