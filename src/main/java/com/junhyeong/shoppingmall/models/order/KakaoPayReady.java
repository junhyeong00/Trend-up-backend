package com.junhyeong.shoppingmall.models.order;

import java.time.LocalDateTime;
import java.util.Objects;

public class KakaoPayReady {
    private String tid;

    private String next_redirect_pc_url;

    private LocalDateTime created_at;

    public KakaoPayReady() {
    }

    public KakaoPayReady(
            String tid,
            String next_redirect_pc_url,
            LocalDateTime created_at) {
        this.tid = tid;
        this.next_redirect_pc_url = next_redirect_pc_url;
        this.created_at = created_at;
    }

    public String getTid() {
        return tid;
    }

    public String getNext_redirect_pc_url() {
        return next_redirect_pc_url;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KakaoPayReady that = (KakaoPayReady) o;
        return Objects.equals(tid, that.tid) && Objects.equals(next_redirect_pc_url, that.next_redirect_pc_url) && Objects.equals(created_at, that.created_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tid, next_redirect_pc_url, created_at);
    }

    @Override
    public String toString() {
        return "KakaoPayReady{" +
                "tid='" + tid + '\'' +
                ", next_redirect_pc_url='" + next_redirect_pc_url + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
