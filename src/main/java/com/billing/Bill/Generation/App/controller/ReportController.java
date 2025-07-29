package com.billing.Bill.Generation.App.controller;

import com.billing.Bill.Generation.App.DTO.Response.UserResponse;
import com.billing.Bill.Generation.App.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @GetMapping("get-today-order-report")
    public UserResponse<?> getTodayOrderReport(){
        try {
           return reportService.sendOrderReport();
        } catch (Exception e) {
            return new UserResponse<>(null,e.getMessage(),false);
        }
    }

    @GetMapping("get-today-stock")
    public UserResponse<?> getTodayStockReport(){
        try{
            return reportService.sendStockReport();
        } catch (Exception e) {
            return new UserResponse<>(null,e.getMessage(),false);
        }
    }
}
