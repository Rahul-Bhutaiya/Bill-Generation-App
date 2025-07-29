package com.billing.Bill.Generation.App.DTO.Response;

public class OrdersList {
    private int orderId;
    private String customerName;
    private String contactNumber;
    private int productId;
    private String productName;
    private int productCount;
    private String category;

    public OrdersList(int orderId, String customerName, String contactNumber, int productId, String productName, int productCount, String category) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.productId = productId;
        this.productName = productName;
        this.productCount = productCount;
        this.category = category;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
