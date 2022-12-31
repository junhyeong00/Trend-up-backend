package com.junhyeong.shoppingmall.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {
    @Test
    void delete() {
        Long reviewId = 1L;
        Review review = Review.fake(reviewId);

        assertThat(review.isDeleted()).isFalse();

        review.delete();

        assertThat(review.isDeleted()).isTrue();
    }

    @Test
    void update() {
        Long reviewId = 1L;
        Review review = Review.fake(reviewId);

        review.update(3D, "좋아요", null);

        assertThat(review.rating()).isEqualTo(3D);
    }
}
