package com.junhyeong.shoppingmall.dtos;

import com.junhyeong.shoppingmall.models.order.Amount;
import com.junhyeong.shoppingmall.models.order.CardInfo;

import java.time.LocalDateTime;

public class KakaoPayApprovalDto {
    private String aid;
    private String tid;
    private String cid;
    private String sid;
    private String partner_order_id;
    private String partner_user_id;
    private String payment_method_type;
    private Amount amount;
    private CardInfo cardInfo;
    private String item_code;
    private Integer quantity;
    private LocalDateTime created_at;
    private LocalDateTime approved_at;
    private String payload;

    public KakaoPayApprovalDto() {
    }

    public KakaoPayApprovalDto(
            String aid,
            String tid,
            String cid,
            String sid,
            String partner_order_id,
            String partner_user_id,
            String payment_method_type,
            Amount amount,
            CardInfo cardInfo,
            String item_code,
            Integer quantity,
            LocalDateTime created_at,
            LocalDateTime approved_at,
            String payload) {
        this.aid = aid;
        this.tid = tid;
        this.cid = cid;
        this.sid = sid;
        this.partner_order_id = partner_order_id;
        this.partner_user_id = partner_user_id;
        this.payment_method_type = payment_method_type;
        this.amount = amount;
        this.cardInfo = cardInfo;
        this.item_code = item_code;
        this.quantity = quantity;
        this.created_at = created_at;
        this.approved_at = approved_at;
        this.payload = payload;
    }

    public String getAid() {
        return aid;
    }

    public String getTid() {
        return tid;
    }

    public String getCid() {
        return cid;
    }

    public String getSid() {
        return sid;
    }

    public String getPartner_order_id() {
        return partner_order_id;
    }

    public String getPartner_user_id() {
        return partner_user_id;
    }

    public String getPayment_method_type() {
        return payment_method_type;
    }

    public Amount getAmount() {
        return amount;
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public String getItem_code() {
        return item_code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getApproved_at() {
        return approved_at;
    }

    public String getPayload() {
        return payload;
    }
}
