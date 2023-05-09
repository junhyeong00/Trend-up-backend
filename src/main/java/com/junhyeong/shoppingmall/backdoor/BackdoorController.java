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
                1, "데스크탑");

        jdbcTemplate.update("INSERT INTO " +
                        "category(id, name)" +
                        "VALUES(?, ?)",
                2, "노트북");

        jdbcTemplate.update("INSERT INTO " +
                        "category(id, name)" +
                        "VALUES(?, ?)",
                3, "스마트폰");

        jdbcTemplate.update("INSERT INTO " +
                        "category(id, name)" +
                        "VALUES(?, ?)",
                4, "악세사리");

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                1, 3, "갤럭시 s22", "갤럭시 s22", 150000L,
                "https://res.cloudinary.com/dywsfe5du/image/upload/v1674029488/%E1%84%80%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%86%A8%E1%84%89%E1%85%B5_22_icpv0p.jpg", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(1, '256GB', 0, 1)"
        );

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(2, '512GB', 50000, 1)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                2, 2, "LG gram +view", "LG gram +view", 200000L,
                "https://res.cloudinary.com/dywsfe5du/image/upload/v1674029921/LG_gram_view_lanqy7.jpg", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(3, '블랙', 0, 2)"
        );

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(4, '실버', 0, 2)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                3, 2, "갤럭시북", "갤럭시북", 100000L,
                "https://res.cloudinary.com/dywsfe5du/image/upload/v1674006512/%E1%84%80%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%86%A8%E1%84%89%E1%85%B5_%E1%84%87%E1%85%AE%E1%86%A8_jqmwaj.jpg", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(5, '실버', 0, 3)"
        );

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(6, '그린파이트', 0, 3)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                4, 3, "iPhone 14 Pro", "iPhone 14 Pro", 120000L,
                "http://res.cloudinary.com/dywsfe5du/image/upload/v1674102067/ycf3kleoouac1uvhsbcl.png", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(7, '실버', 0, 4)"
        );

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(8, '그린파이트', 0, 4)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                5, 4, "갤럭시 버즈2 Pro", "갤럭시 버즈2 Pro", 80000L,
                "https://res.cloudinary.com/dywsfe5du/image/upload/v1674398281/%E1%84%80%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%86%A8%E1%84%89%E1%85%B5_%E1%84%87%E1%85%A5%E1%84%8C%E1%85%B32_Pro_nlcsws.webp", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(9, '블랙', 0, 5)"
        );

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(10, '화이트', 0, 5)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                6, 4, "갤럭시 버즈2", "갤럭시 버즈2", 60000L,
                "https://res.cloudinary.com/dywsfe5du/image/upload/v1674398456/%E1%84%80%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%86%A8%E1%84%89%E1%85%B5_%E1%84%87%E1%85%A5%E1%84%8C%E1%85%B32_fmnosl.avif", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(11, '블랙', 0, 6)"
        );

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(12, '바이올렛', 0, 6)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                7, 2, "갤럭시북 Flex2", "갤럭시북 Flex2", 140000L,
                "https://res.cloudinary.com/dywsfe5du/image/upload/v1674398580/%E1%84%80%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%86%A8%E1%84%89%E1%85%B5_%E1%84%87%E1%85%AE%E1%86%A8_Flex2_psn3dj.avif", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(13, '기본', 0, 7)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                8, 2, "갤럭시북2 Pro", "갤럭시북2 Pro", 120000L,
                "https://res.cloudinary.com/dywsfe5du/image/upload/v1674398774/%E1%84%80%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%86%A8%E1%84%89%E1%85%B5_%E1%84%87%E1%85%AE%E1%86%A82_Pro_cos5om.webp", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(14, '기본', 0, 8)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                9, 4, "갤럭시 워치4 메종키츠네 에디션", "갤럭시 워치4 메종키트네 에디션", 300000L,
                "https://res.cloudinary.com/dywsfe5du/image/upload/v1674477813/Galaxy_watch4_%E1%84%86%E1%85%A6%E1%84%8C%E1%85%A9%E1%86%BC_p0gytl.webp", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(15, '기본', 0, 9)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                10, 4, "갤럭시 워치4 클래식", "갤럭시 워치4 클래식", 100000L,
                "https://res.cloudinary.com/dywsfe5du/image/upload/v1674478160/Galaxy_watch4_%E1%84%8F%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A2%E1%84%89%E1%85%B5%E1%86%A8_nlxjbj.avif", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(16, '기본', 0, 10)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                11, 4, "갤럭시 워치5", "갤럭시 워치5", 150000L,
                "https://res.cloudinary.com/dywsfe5du/image/upload/v1674477389/%E1%84%80%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%86%A8%E1%84%89%E1%85%B5_%E1%84%8B%E1%85%AF%E1%84%8E%E1%85%B55_tjwjgx.avif", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(17, '기본', 0, 11)"
        );

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, category_id, name, description, price, image, create_at)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                12, 2, "갤럭시탭 s8 ultra", "갤럭시탭 s8", 100000L,
                "https://res.cloudinary.com/dywsfe5du/image/upload/v1674477473/%E1%84%80%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%86%A8%E1%84%89%E1%85%B5_%E1%84%90%E1%85%A2%E1%86%B8_s8_ultra_ddwm3w.avif", now);

        jdbcTemplate.execute("INSERT INTO " +
                "option(id, name, option_price, product_id)" +
                "VALUES(18, '기본', 0, 12)"
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
