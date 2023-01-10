package com.junhyeong.shoppingmall.utils;

import com.junhyeong.shoppingmall.dtos.OrderProductDto;
import com.junhyeong.shoppingmall.exceptions.KakaoPayReadyException;
import com.junhyeong.shoppingmall.models.vo.KakaoPayApproval;
import com.junhyeong.shoppingmall.models.vo.KakaoPayReady;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@Transactional
public class KaKaoPay {
    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReady kakaoPayReady;

    private KakaoPayApproval kakaoPayApproval;

    private String orderId;

    private Long userId;

    private List<OrderProductDto> orderProducts;

    public String kakaoPayReady(String orderId,
                                Long userId,
                                String productName,
                                Long quantity,
                                Long orderPayment,
                                List<OrderProductDto> orderProducts) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderProducts = orderProducts;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "1ca2844ddd9d0eaaa771e800a7fc2fc6");
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", orderId);
        params.add("partner_user_id", String.valueOf(userId));
        params.add("item_name", productName);
        params.add("quantity", String.valueOf(quantity));
        params.add("total_amount", String.valueOf(orderPayment));
        params.add("tax_free_amount", "10000");
        params.add("approval_url", "http://localhost:8080/order/success");
        params.add("cancel_url", "http://localhost:8080/order/cancel");
        params.add("fail_url", "http://localhost:8080/order/fail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);

        try {
            kakaoPayReady = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReady.class);

            return kakaoPayReady.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            throw new KakaoPayReadyException(e);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public KakaoPayApproval kakaoPayInfo(String pg_token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "1ca2844ddd9d0eaaa771e800a7fc2fc6");
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReady.getTid());
        params.add("partner_order_id", orderId);
        params.add("partner_user_id", String.valueOf(userId));
        params.add("pg_token", pg_token);

        HttpEntity<MultiValueMap<String, String>> requestBody = new HttpEntity<>(params, headers);

        try {
            return restTemplate
                    .postForObject(new URI(HOST + "/v1/payment/approve"),
                            requestBody, KakaoPayApproval.class);

        } catch (RestClientException e) {
            throw new KakaoPayReadyException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
