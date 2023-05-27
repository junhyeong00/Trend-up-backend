package com.junhyeong.shoppingmall.dtos;

import java.util.List;

public class SalesDto {
    private List<CoordinateDto> salesData;
    private Long totalSales;
    private Long monthlySales;

    public SalesDto(List<CoordinateDto> salesData, Long totalSales, Long monthlySales) {
        this.salesData = salesData;
        this.totalSales = totalSales;
        this.monthlySales = monthlySales;
    }

    public List<CoordinateDto> getSalesData() {
        return salesData;
    }

    public Long getTotalSales() {
        return totalSales;
    }

    public Long getMonthlySales() {
        return monthlySales;
    }
}
