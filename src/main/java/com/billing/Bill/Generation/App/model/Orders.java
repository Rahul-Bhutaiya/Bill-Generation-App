package com.billing.Bill.Generation.App.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "customer_name",length = 50,nullable = false)
    private String customerName;

    @Column(name = "contact_number",length = 20,nullable = false)
    private String contactNumber;

    @JoinColumn(name = "product_id",nullable = false)
    @ManyToOne
    private Product product;

    @Column(name = "product_count",nullable = false)
    private int productCount;

    @Column(name = "created_at",nullable = false,columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false,columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }

    public Orders(String customerName, String contactNumber, Product product, int productCount) {
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.product = product;
        this.productCount = productCount;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}
