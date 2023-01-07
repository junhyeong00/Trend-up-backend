package com.junhyeong.shoppingmall.models;

import java.util.Objects;

public class CardInfo {
    private String purchase_corp;

    private String purchase_corp_code;

    private String issuer_corp;

    private String issuer_corp_code;

    private String kakaopay_purchase_corp;

    private String kakaopay_purchase_corp_code;

    private String kakaopay_issuer_corp;

    private String kakaopay_issuer_corp_code;

    private String bin;

    private String card_type;

    private String install_month;

    private String approved_id;

    private String card_mid;

    private String interest_free_install;

    private String card_item_code;

    public CardInfo(String purchase_corp,
                    String purchase_corp_code,
                    String issuer_corp,
                    String issuer_corp_code,
                    String kakaopay_purchase_corp,
                    String kakaopay_purchase_corp_code,
                    String kakaopay_issuer_corp,
                    String kakaopay_issuer_corp_code,
                    String bin, String card_type,
                    String install_month,
                    String approved_id,
                    String card_mid,
                    String interest_free_install,
                    String card_item_code) {
        this.purchase_corp = purchase_corp;
        this.purchase_corp_code = purchase_corp_code;
        this.issuer_corp = issuer_corp;
        this.issuer_corp_code = issuer_corp_code;
        this.kakaopay_purchase_corp = kakaopay_purchase_corp;
        this.kakaopay_purchase_corp_code = kakaopay_purchase_corp_code;
        this.kakaopay_issuer_corp = kakaopay_issuer_corp;
        this.kakaopay_issuer_corp_code = kakaopay_issuer_corp_code;
        this.bin = bin;
        this.card_type = card_type;
        this.install_month = install_month;
        this.approved_id = approved_id;
        this.card_mid = card_mid;
        this.interest_free_install = interest_free_install;
        this.card_item_code = card_item_code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardInfo cardInfo = (CardInfo) o;
        return Objects.equals(purchase_corp, cardInfo.purchase_corp) && Objects.equals(purchase_corp_code, cardInfo.purchase_corp_code) && Objects.equals(issuer_corp, cardInfo.issuer_corp) && Objects.equals(issuer_corp_code, cardInfo.issuer_corp_code) && Objects.equals(kakaopay_purchase_corp, cardInfo.kakaopay_purchase_corp) && Objects.equals(kakaopay_purchase_corp_code, cardInfo.kakaopay_purchase_corp_code) && Objects.equals(kakaopay_issuer_corp, cardInfo.kakaopay_issuer_corp) && Objects.equals(kakaopay_issuer_corp_code, cardInfo.kakaopay_issuer_corp_code) && Objects.equals(bin, cardInfo.bin) && Objects.equals(card_type, cardInfo.card_type) && Objects.equals(install_month, cardInfo.install_month) && Objects.equals(approved_id, cardInfo.approved_id) && Objects.equals(card_mid, cardInfo.card_mid) && Objects.equals(interest_free_install, cardInfo.interest_free_install) && Objects.equals(card_item_code, cardInfo.card_item_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchase_corp, purchase_corp_code, issuer_corp, issuer_corp_code, kakaopay_purchase_corp, kakaopay_purchase_corp_code, kakaopay_issuer_corp, kakaopay_issuer_corp_code, bin, card_type, install_month, approved_id, card_mid, interest_free_install, card_item_code);
    }

    public String getPurchase_corp() {
        return purchase_corp;
    }

    public String getPurchase_corp_code() {
        return purchase_corp_code;
    }

    public String getIssuer_corp() {
        return issuer_corp;
    }

    public String getIssuer_corp_code() {
        return issuer_corp_code;
    }

    public String getKakaopay_purchase_corp() {
        return kakaopay_purchase_corp;
    }

    public String getKakaopay_purchase_corp_code() {
        return kakaopay_purchase_corp_code;
    }

    public String getKakaopay_issuer_corp() {
        return kakaopay_issuer_corp;
    }

    public String getKakaopay_issuer_corp_code() {
        return kakaopay_issuer_corp_code;
    }

    public String getBin() {
        return bin;
    }

    public String getCard_type() {
        return card_type;
    }

    public String getInstall_month() {
        return install_month;
    }

    public String getApproved_id() {
        return approved_id;
    }

    public String getCard_mid() {
        return card_mid;
    }

    public String getInterest_free_install() {
        return interest_free_install;
    }

    public String getCard_item_code() {
        return card_item_code;
    }
}
