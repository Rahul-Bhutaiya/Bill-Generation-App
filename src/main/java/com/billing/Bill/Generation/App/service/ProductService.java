package com.billing.Bill.Generation.App.service;

import com.billing.Bill.Generation.App.DTO.Request.ProductRequest;
import com.billing.Bill.Generation.App.DTO.Response.PaginatedData;
import com.billing.Bill.Generation.App.DTO.Response.StockList;
import com.billing.Bill.Generation.App.DTO.Response.UserResponse;
import com.billing.Bill.Generation.App.model.Category;
import com.billing.Bill.Generation.App.model.Product;
import com.billing.Bill.Generation.App.repository.CategoryRepo;
import com.billing.Bill.Generation.App.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;

    public UserResponse<?> addAllProducts(List<Product> productList){
        productRepo.saveAll(productList);
        return new UserResponse<>(null,"All products added successfully",true);
    }

    public UserResponse<?> addProduct(ProductRequest productRequest){
        String categoryName = productRequest.getCategoryName();
        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException("Category name cannot be null or blank");
        }

        // getting category from db, so new category will not be created by JPA
        Category category = categoryRepo.findById(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product newProduct = new Product(productRequest.getName(),productRequest.getPrice(),productRequest.getStockQuantity(),category);
        productRepo.save(newProduct);
        return new UserResponse<>(null,"Product Added Successfully",true);
    }

    public PaginatedData<?> getProducts(int page) {
        int limit = 5;
        int offset = (page-1)*limit;
        List<StockList> productList = productRepo.getProductList(offset,limit);
        if(productList.isEmpty()){
            throw new RuntimeException("Invalid Page Number");
        }

        // getting total number of products in products table
        long totalProducts = productRepo.count();

        return new PaginatedData<>(
                productList,
                "Product get successfully",
                true,
                totalProducts,
                page,
                limit
        );
    }
}
