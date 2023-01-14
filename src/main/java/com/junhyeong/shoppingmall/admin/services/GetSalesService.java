package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.dtos.CoordinateDto;
import com.junhyeong.shoppingmall.dtos.SalesDto;
import com.junhyeong.shoppingmall.models.Order;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import com.junhyeong.shoppingmall.specifications.OrderSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GetSalesService {
    private final OrderRepository orderRepository;

    public GetSalesService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public SalesDto sales() {
        List<CoordinateDto> sales = new ArrayList<>();

        LocalDateTime date = LocalDateTime.now().minusMonths(1);

        Long monthlySales = 0L;

        while (date.isBefore(LocalDateTime.now())) {
            Specification<Order> spec = Specification
                    .where(OrderSpecification.betweenCreatedDatetime(date, date.plusHours(23).plusMinutes(59)));

            List<Order> orders = orderRepository.findAll(spec);

            Long amount = orders.stream().mapToLong(Order::payment)
                            .reduce(0L, Long::sum);

            monthlySales += amount;

            CoordinateDto coordinateDto = new CoordinateDto(date.format(DateTimeFormatter.ofPattern("MM-dd")), amount);
            sales.add(coordinateDto);

            date = date.plusDays(1);
        }

        Long totalSales = calcTotalSales();

        return new SalesDto(sales, totalSales, monthlySales);
    }

    private long calcTotalSales() {
        return orderRepository.findAll().stream().mapToLong(Order::payment)
                .reduce(0L, Long::sum);
    }
}
