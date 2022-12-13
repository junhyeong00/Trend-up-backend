package com.junhyeong.shoppingmall.services;

import com.junhyeong.shoppingmall.dtos.OrderProductDto;
import com.junhyeong.shoppingmall.exceptions.OptionNotFound;
import com.junhyeong.shoppingmall.exceptions.OrderFailed;
import com.junhyeong.shoppingmall.exceptions.ProductNotFound;
import com.junhyeong.shoppingmall.exceptions.UserNotFound;
import com.junhyeong.shoppingmall.models.Address;
import com.junhyeong.shoppingmall.models.Option;
import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.models.OrderProduct;
import com.junhyeong.shoppingmall.models.Product;
import com.junhyeong.shoppingmall.models.User;
import com.junhyeong.shoppingmall.models.UserName;
import com.junhyeong.shoppingmall.repositories.OptionRepository;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CreateOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public CreateOrderService(OrderRepository orderRepository, UserRepository userRepository,
                              ProductRepository productRepository, OptionRepository optionRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    public Order createOrder(UserName userName, String phoneNumber,
                             String receiver, Long payment,
                             Long totalPrice, Long deliveryFee,
                             List<OrderProductDto> orderProductDtos,
                             String deliveryRequest, Address address) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        List<OrderProduct> orderProducts = new ArrayList<>();

        for (OrderProductDto orderProductDto : orderProductDtos) {
            Product product = productRepository.findById(orderProductDto.getProductId())
                    .orElseThrow(ProductNotFound::new);
            Option option = optionRepository.findById(orderProductDto.getOptionId())
                    .orElseThrow(OptionNotFound::new);

            Long productPrice = product.price() + option.optionPrice();

            OrderProduct orderProduct = new OrderProduct(
                    product.id(), product.name(), productPrice, option.name(),
                    orderProductDto.getQuantity(), product.image());

            orderProducts.add(orderProduct);

            if (option.stockQuantity() < orderProductDto.getQuantity()) {
                throw new OrderFailed(product.name() + " - " + option.name() + "의 재고가 부족합니다");
            }
        }

        orderProductDtos.stream().forEach((product) -> {
            Option option = optionRepository.findById(product.getOptionId())
                    .orElseThrow(OptionNotFound::new);

            option.sell(product.getQuantity());
        });

        Order order = new Order(
                user.id(), phoneNumber, receiver,
                payment, totalPrice, deliveryFee,
                deliveryRequest, orderProducts, address);

        orderRepository.save(order);
        return order;
    }
}
