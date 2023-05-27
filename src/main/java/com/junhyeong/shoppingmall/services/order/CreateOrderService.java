package com.junhyeong.shoppingmall.services.order;

import com.junhyeong.shoppingmall.dtos.CreateOrderProductDto;
import com.junhyeong.shoppingmall.dtos.OrderProductDto;
import com.junhyeong.shoppingmall.dtos.OrderRequest;
import com.junhyeong.shoppingmall.exceptions.OptionNotFound;
import com.junhyeong.shoppingmall.exceptions.ProductNotFound;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.order.Address;
import com.junhyeong.shoppingmall.models.option.Option;
import com.junhyeong.shoppingmall.models.order.Order;
import com.junhyeong.shoppingmall.models.order.OrderProduct;
import com.junhyeong.shoppingmall.models.order.PhoneNumber;
import com.junhyeong.shoppingmall.models.product.Product;
import com.junhyeong.shoppingmall.models.user.User;
import com.junhyeong.shoppingmall.models.user.UserName;
import com.junhyeong.shoppingmall.repositories.OptionRepository;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import com.junhyeong.shoppingmall.utils.KaKaoPay;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CreateOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;
    private final KaKaoPay kaKaoPay;

    public CreateOrderService(OrderRepository orderRepository, UserRepository userRepository,
                              ProductRepository productRepository, OptionRepository optionRepository, KaKaoPay kaKaoPay) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
        this.kaKaoPay = kaKaoPay;
    }

    @Transactional
    public String createOrder(UserName userName, OrderRequest orderRequest) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        List<OrderProduct> orderProducts = getOrderProducts(orderRequest);

        Long orderId = Long.valueOf((time() + randomNumber()));

        Order order = new Order(
                orderId, user.id(),
                orderRequest.getPhoneNumber(), orderRequest.getReceiver(),
                orderRequest.getPayment(), orderRequest.getTotalPrice(),
                orderRequest.getDeliveryFee(), orderRequest.getDeliveryRequest(),
                orderProducts, orderRequest.getAddress());

        orderRepository.save(order);

        return getKakaoPayUrl(orderRequest, user, orderProducts, orderId);
    }

    private String time() {
        Date current = Calendar.getInstance().getTime();

        SimpleDateFormat date = new SimpleDateFormat("yyMMdd");

        return date.format(current);
    }

    private String randomNumber() {
        Random random = new Random();

        String randomNumber = "";

        for (int i = 0; i < 6; i += 1) {
            randomNumber += random.nextInt(9);
        }

        return randomNumber;
    }

    private List<OrderProduct> getOrderProducts(OrderRequest orderRequest) {
        return orderRequest.getOrderProducts().stream()
                .map((orderProductDto) -> {
                    Product product = productRepository.findById(orderProductDto.getProductId())
                            .orElseThrow(ProductNotFound::new);
                    Option option = optionRepository.findById(orderProductDto.getOptionId())
                            .orElseThrow(OptionNotFound::new);

                    Long productPrice = product.price() + option.optionPrice();

                    return new OrderProduct(
                            product.id(), product.name(), productPrice, option.id(), option.name(),
                            orderProductDto.getQuantity(), product.image());
                }).toList();
    }

    private String getKakaoPayUrl(OrderRequest orderRequest, User user, List<OrderProduct> orderProducts, Long orderId) {
        String productName = orderProducts.get(0).productName();
        Long quantity = orderProducts.get(0).productQuantity();

        if (orderProducts.size() >= 2) {
            productName =
                    orderProducts.get(0).productName() + " 외 " +
                            "" + (orderProducts.size() - 1) + "건";

            for (OrderProduct orderProduct : orderProducts) {
                quantity = 0L;
                quantity += orderProduct.productQuantity();
            }
        }

        List<OrderProductDto> orderItemDtos = orderProducts.stream()
                .map(OrderProduct::toOrderProductDto).toList();

        return kaKaoPay.kakaoPayReady(
                orderId.toString(),
                user.id(),
                productName,
                quantity,
                orderRequest.getPayment(),
                orderItemDtos
        );
    }
}
