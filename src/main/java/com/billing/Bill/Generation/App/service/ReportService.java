package com.billing.Bill.Generation.App.service;

import com.billing.Bill.Generation.App.DTO.Response.OrdersList;
import com.billing.Bill.Generation.App.DTO.Response.StockList;
import com.billing.Bill.Generation.App.DTO.Response.UserResponse;
import com.billing.Bill.Generation.App.model.Product;
import com.billing.Bill.Generation.App.repository.OrdersRepo;
import com.billing.Bill.Generation.App.repository.ProductRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    OrdersRepo ordersRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 0 18 * * ?") // daily at 6:00 PM
    public UserResponse<?> sendOrderReport() throws IOException, MessagingException {
        List<OrdersList> ordersLists = getTodayOrders();
        byte[] csvBytes = generateCsv(ordersLists);
        String adminEmail = "work.rahulbhutaiya@gmail.com";
        sendCsvReport(csvBytes,adminEmail,ordersLists,false);

        if(ordersLists.isEmpty()){
            return new UserResponse<>(null,"No orders placed today.",true);
        }
        return new UserResponse<>(null,"Report send to your email successfully",true);

    }

    @Scheduled(cron = "0 0 18 * * ?") // daily at 6:00 PM
    public UserResponse<?> sendStockReport() throws IOException, MessagingException {
        List<StockList> productList = getStockList();
        byte[] productCSV = generateAllProductCsv(productList);
        String adminEmail = "work.rahulbhutaiya@gmail.com";
        sendCsvReport(productCSV,adminEmail,productList,true);
        if(productList.isEmpty()){
            return new UserResponse<>(null,"No Product Found.",true);
        }
        return new UserResponse<>(null,"Report send to your email successfully",true);
    }

    private List<OrdersList> getTodayOrders(){
        return ordersRepo.getTodayOrders();
    }

    private byte[] generateCsv(List<OrdersList> orders) throws IOException {
        StringWriter writer = new StringWriter();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("Order ID", "Customer Name", "Contact Number","Product Id","Product Name", "Quantity", "Category"))) {
            for (OrdersList order : orders) {
                csvPrinter.printRecord(
                        order.getOrderId(),
                        order.getCustomerName(),
                        order.getContactNumber(),
                        order.getProductId(),
                        order.getProductName(),
                        order.getProductCount(),
                        order.getCategory()
                );
            }
        }
        return writer.toString().getBytes(StandardCharsets.UTF_8);
    }


    private void sendCsvReport(byte[] csvBytes, String adminEmail,List<?> orders,boolean isStockReport) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(adminEmail);
        String subject = isStockReport?"🧾 Daily Stock Report":"🧾 Daily Orders Report";
        helper.setSubject(subject);

        String description;
        if(isStockReport){
            description = "Attached is Today's Stock Report.";
        }
        else{
            if(orders.isEmpty()){
                description = "No orders were placed today. CSV file contains headers only.";
            }
            else{
                description = "Attached is the daily report for orders placed today.";
            }
        }
        helper.setText(description);

        String fileName = isStockReport?"DailyStockReport.csv":"DailyOrdersReport.csv";
        helper.addAttachment(fileName, new ByteArrayResource(csvBytes));

        mailSender.send(message);
    }

    private List<StockList> getStockList(){
        return productRepo.getAllProductStock();
    }

    private byte[] generateAllProductCsv(List<StockList> products) throws IOException {
        StringWriter writer = new StringWriter();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("Product ID", "Product Name", "Price","Category","Stock Quantity"))) {
            for (StockList product : products) {
                csvPrinter.printRecord(
                        product.getProductId(),
                        product.getName(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getStockQuantity()
                );
            }
        }
        return writer.toString().getBytes(StandardCharsets.UTF_8);
    }


}
