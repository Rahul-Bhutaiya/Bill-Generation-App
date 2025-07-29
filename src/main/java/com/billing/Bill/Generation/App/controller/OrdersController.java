package com.billing.Bill.Generation.App.controller;

import com.billing.Bill.Generation.App.DTO.Request.OrderRequest;
import com.billing.Bill.Generation.App.DTO.Response.UserResponse;
import com.billing.Bill.Generation.App.Utils.BillingData;
import com.billing.Bill.Generation.App.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrdersController {
    @Autowired
    OrdersService ordersService;

    // User will place the order using this API
    @PostMapping("make-order")
    public UserResponse<BillingData> makeOrder(@RequestBody OrderRequest orderRequest){
        try{
            return ordersService.placeOrder(orderRequest);
        } catch (Exception e) {
            return new UserResponse<>(null,e.getMessage(),false);
        }
    }
}
