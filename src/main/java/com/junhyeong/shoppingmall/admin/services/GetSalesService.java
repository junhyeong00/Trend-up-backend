package com.junhyeong.shoppingmall.admin.services;

import com.junhyeong.shoppingmall.dtos.CoordinateDto;
import com.junhyeong.shoppingmall.dtos.SalesDto;
import com.junhyeong.shoppingmall.models.order.Order;
import com.junhyeong.shoppingmall.repositories.OrderRepository;
import com.junhyeong.shoppingmall.specifications.OrderSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetSalesService {
    private final OrderRepository orderRepository;

    public GetSalesService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public SalesDto sales() {
        LocalDateTime date = LocalDateTime.now().minusMonths(1);

        List<CoordinateDto> sales = calcCoordinateDtos(date);

        Long monthlySales = calcMonthlySales();

        Long totalSales = calcTotalSales();

        return new SalesDto(sales, totalSales, monthlySales);
    }

    private Long calcMonthlySales() {
        LocalDateTime date = LocalDateTime.now().minusMonths(1);

        Specification<Order> spec = Specification.where(OrderSpecification
                .betweenCreatedDatetime(date, LocalDateTime.now()));

        Long monthlySales = orderRepository.findAll(spec).stream().mapToLong(Order::payment)
                .reduce(0L, Long::sum);
        return monthlySales;
    }

    private List<CoordinateDto> calcCoordinateDtos(LocalDateTime date) {
        List<CoordinateDto> sales = new ArrayList<>();

        while (date.isBefore(LocalDateTime.now())) {
            Specification<Order> spec = Specification.where(OrderSpecification
                    .betweenCreatedDatetime(date.withHour(0).withMinute(0), date.withHour(23).withMinute(59)));

            List<Order> orders = orderRepository.findAll(spec);

            Long amount = orders.stream().mapToLong(Order::payment)
                    .reduce(0L, Long::sum);

            CoordinateDto coordinateDto = new CoordinateDto(date.format(DateTimeFormatter.ofPattern("MM-dd")), amount);
            sales.add(coordinateDto);

            date = date.plusDays(1);
        }

        return sales;
    }

    private long calcTotalSales() {
        return orderRepository.findAll().stream().mapToLong(Order::payment)
                .reduce(0L, Long::sum);
    }
}
