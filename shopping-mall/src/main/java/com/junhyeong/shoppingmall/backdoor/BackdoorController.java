package com.junhyeong.shoppingmall.backdoor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("backdoor")
public class BackdoorController {
    private final JdbcTemplate jdbcTemplate;

    public BackdoorController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("setup-products")
    public String setupProducts() {
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.execute("DELETE FROM product");

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category, name, description, product_count, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                1, "남성 패션", "가디건", "가볍다", "4", 50000L, null, now);

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category, name, description, product_count, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                2, "남성 패션", "신발", "가볍다", "7", 40000L, null, now);

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category, name, description, product_count, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                3, "화장품/미용", "수분크림", "촉촉하다", "20", 20000L, null, now);
        return "OK";
    }
}
