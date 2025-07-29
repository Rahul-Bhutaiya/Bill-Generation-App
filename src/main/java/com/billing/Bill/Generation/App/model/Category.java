package com.billing.Bill.Generation.App.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "category_name",nullable = false)
    private String categoryName;

    @Min(0)
    @Max(100)
    @Column(name = "gst_percentage",nullable = false)
    private int gstPercentage;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> products;

    @Column(name = "create_at",columnDefinition = "timestamp default current_timestamp",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",columnDefinition = "timestamp default current_timestamp on update current_timestamp",nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Category(String categoryName, int gstPercentage) {
        this.categoryName = categoryName;
        this.gstPercentage = gstPercentage;
    }

    public Category(){}

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getGstPercentage() {
        return gstPercentage;
    }

    public void setGstPercentage(int gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
