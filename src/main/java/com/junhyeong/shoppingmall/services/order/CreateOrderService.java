package com.junhyeong.shoppingmall.services.order;

import com.junhyeong.shoppingmall.dtos.CreateOrderProductDto;
import com.junhyeong.shoppingmall.dtos.OrderProductDto;
import com.junhyeong.shoppingmall.exceptions.OptionNotFound;
import com.junhyeong.shoppingmall.exceptions.ProductNotFound;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.vo.Address;
import com.junhyeong.shoppingmall.models.Option;
import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.models.vo.OrderProduct;
import com.junhyeong.shoppingmall.models.vo.PhoneNumber;
import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.vo.UserName;
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
import java.util.UUID;

@Service
@Transactional
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

    public String createOrder(UserName userName, PhoneNumber phoneNumber,
                              String receiver, Long payment,
                              Long totalPrice, Long deliveryFee,
                              List<CreateOrderProductDto> orderProductDtos,
                              String deliveryRequest, Address address) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        List<OrderProduct> orderProducts = new ArrayList<>();

        for (CreateOrderProductDto orderProductDto : orderProductDtos) {
            Product product = productRepository.findById(orderProductDto.getProductId())
                    .orElseThrow(ProductNotFound::new);
            Option option = optionRepository.findById(orderProductDto.getOptionId())
                    .orElseThrow(OptionNotFound::new);

            Long productPrice = product.price() + option.optionPrice();

            OrderProduct orderProduct = new OrderProduct(
                    product.id(), product.name(), productPrice, option.id(), option.name(),
                    orderProductDto.getQuantity(), product.image());

            orderProducts.add(orderProduct);

            // TODO 재고 관리
//            if (option.stockQuantity() < orderProductDto.getQuantity()) {
//                throw new OrderFailed(product.name() + " - " + option.name() + "의 재고가 부족합니다");
//            }
//
//            option.reduceStock(orderProductDto.getQuantity());
        }
//
//        orderProductDtos.stream().forEach((product) -> {
//            Option option = optionRepository.findById(product.getOptionId())
//                    .orElseThrow(OptionNotFound::new);
//
//        });

        Long orderId = Long.valueOf((time() + randomNumber()));

        Order order = new Order(orderId,
                user.id(), phoneNumber, receiver,
                payment, totalPrice, deliveryFee,
                deliveryRequest, orderProducts, address);

        orderRepository.save(order);

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

        List<OrderProductDto> orderItemDtos = orderProducts.stream().map((orderProduct) -> orderProduct.toOrderProductDto(false)).toList();

        return kaKaoPay.kakaoPayReady(
                orderId.toString(),
                user.id(),
                productName,
                quantity,
                payment,
                orderItemDtos
        );
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
}
