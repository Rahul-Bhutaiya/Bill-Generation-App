package com.billing.Bill.Generation.App.controller;

import com.billing.Bill.Generation.App.DTO.Request.ProductRequest;
import com.billing.Bill.Generation.App.DTO.Response.PaginatedData;
import com.billing.Bill.Generation.App.DTO.Response.UserResponse;
import com.billing.Bill.Generation.App.model.Product;
import com.billing.Bill.Generation.App.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    ProductService productService;

    // add multiple products
    @PostMapping("add-all-products")
    public UserResponse<?> addAllProducts(@RequestBody List<Product> productList){
        try {
            return productService.addAllProducts(productList);
        } catch (Exception e) {
            return new UserResponse<>(null,e.getMessage(),false);
        }
    }

    // add single product
    @PostMapping("add-product")
    public UserResponse<?> addProduct(@RequestBody ProductRequest productRequest){
        try{
            return productService.addProduct(productRequest);
        } catch (Exception e) {
            return new UserResponse<>(null,e.getMessage(),false);
        }
    }

    // get list of products and applied pagination
    @GetMapping("get-products")
    public PaginatedData<?> getProducts(@RequestParam(name = "page") int page){
        try{
            return productService.getProducts(page);
        } catch (Exception e) {
            return new PaginatedData<>(
                    null,
                    e.getMessage(),
                    false,
                    0,
                    page,
                    0
            );
        }
    }
}
