package com.billing.Bill.Generation.App.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
public class BillingData {
    private BigDecimal productBasePrice;
    private int gstPercentage;
    private BigDecimal perProductFinalPrice;
    private BigDecimal gstAmount;
    private int productCount;
    private BigDecimal finalPrice;

    public BillingData(int productCount, int gstPercentage, BigDecimal productBasePrice) {
        this.productCount = productCount;
        this.gstPercentage = gstPercentage;
        this.productBasePrice = productBasePrice;
        this.calculateBillingDetails();
    }

    public void calculateBillingDetails(){
        this.gstAmount =this.productBasePrice.multiply(BigDecimal.valueOf(this.gstPercentage)).
                divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        this.perProductFinalPrice = this.productBasePrice.add(this.gstAmount);

        this.finalPrice = this.perProductFinalPrice.multiply(BigDecimal.valueOf(this.productCount));
    }

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }

    public BigDecimal getProductBasePrice() {
        return productBasePrice;
    }

    public void setProductBasePrice(BigDecimal productBasePrice) {
        this.productBasePrice = productBasePrice;
    }

    public int getGstPercentage() {
        return gstPercentage;
    }

    public void setGstPercentage(int gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public BigDecimal getPerProductFinalPrice() {
        return perProductFinalPrice;
    }

    public void setPerProductFinalPrice(BigDecimal perProductFinalPrice) {
        this.perProductFinalPrice = perProductFinalPrice;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
}
