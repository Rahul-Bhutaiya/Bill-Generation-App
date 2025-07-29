package com.billing.Bill.Generation.App.DTO.Response;

import java.math.BigDecimal;

public interface StockList {
    public Integer getProductId();
    public String getName();
    public BigDecimal getPrice();
    public Integer getStockQuantity();
    public String getCategory();
}
