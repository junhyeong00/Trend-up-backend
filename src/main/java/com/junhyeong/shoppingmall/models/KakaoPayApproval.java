package com.junhyeong.shoppingmall.models;

import com.junhyeong.shoppingmall.dtos.KakaoPayApprovalDto;

import java.time.LocalDateTime;

public class KakaoPayApproval {
    private String aid;

    private String tid;

    private String cid;

    private String sid;

    private String partner_order_id;

    private String partner_user_id;

    private String payment_method_type;

    private Amount amount;

    private CardInfo cardInfo;

    private String item_name;

    private String item_code;

    private Integer quantity;

    private LocalDateTime created_at;

    private LocalDateTime approved_at;

    private String payload;

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

    public String getItem_name() {
        return item_name;
    }

    public String getItem_code() {
        return item_code;
    }

    public LocalDateTime getApproved_at() {
        return approved_at;
    }

    public String getPayload() {
        return payload;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public KakaoPayApproval() {
    }

    public KakaoPayApproval(
            String aid,
            String tid,
            String cid,
            String sid,
            String partner_order_id,
            String partner_user_id,
            String payment_method_type,
            Amount amount,
            CardInfo cardInfo,
            String item_name,
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
        this.item_name = item_name;
        this.item_code = item_code;
        this.quantity = quantity;
        this.created_at = created_at;
        this.approved_at = approved_at;
        this.payload = payload;
    }

    public KakaoPayApprovalDto toDto() {
        return new KakaoPayApprovalDto(aid,tid,cid,sid,partner_order_id,partner_user_id
                ,payment_method_type,amount,cardInfo,item_name,quantity,created_at,approved_at,payload);
    }

    @Override
    public String toString() {
        return "KakaoPayApproval{" +
                "aid='" + aid + '\'' +
                ", tid='" + tid + '\'' +
                ", cid='" + cid + '\'' +
                ", sid='" + sid + '\'' +
                ", partner_order_id='" + partner_order_id + '\'' +
                ", partner_user_id='" + partner_user_id + '\'' +
                ", payment_method_type='" + payment_method_type + '\'' +
                ", amount=" + amount +
                ", cardInfo=" + cardInfo +
                ", item_name='" + item_name + '\'' +
                ", item_code='" + item_code + '\'' +
                ", quantity=" + quantity +
                ", created_at=" + created_at +
                ", approved_at=" + approved_at +
                ", payload='" + payload + '\'' +
                '}';
    }
}
