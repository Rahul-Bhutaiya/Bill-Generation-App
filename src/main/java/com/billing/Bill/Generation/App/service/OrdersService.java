package com.billing.Bill.Generation.App.service;

import com.billing.Bill.Generation.App.DTO.Request.OrderRequest;
import com.billing.Bill.Generation.App.DTO.Response.UserResponse;
import com.billing.Bill.Generation.App.Utils.BillingData;
import com.billing.Bill.Generation.App.configs.TwilioConfigs;
import com.billing.Bill.Generation.App.model.Category;
import com.billing.Bill.Generation.App.model.Orders;
import com.billing.Bill.Generation.App.model.Product;
import com.billing.Bill.Generation.App.repository.CategoryRepo;
import com.billing.Bill.Generation.App.repository.OrdersRepo;
import com.billing.Bill.Generation.App.repository.ProductRepo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class OrdersService {
    @Autowired
    OrdersRepo ordersRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    TwilioConfigs twilioConfigs;


//    @Value("${twilio.account.sid}")
//    private String ACCOUNT_SID;
//
//    @Value("${twilio.auth.token}")
//    private String AUTH_TOKEN;
//
//    @Value("${twilio.phone.whatsapp}")
//    private String FROM_PHONE_WHATSAPP;
//
//    @Value("${twilio.phone.sms}")
//    private String FROM_PHONE_SMS;

    @Transactional
    public UserResponse<BillingData> placeOrder(OrderRequest orderRequest) {
        int productId = orderRequest.getProductId();
        if(productId == 0){
            throw new IllegalArgumentException("Product Id cannot be null or blank");
        }
        if(orderRequest.getProductCount()<=0){
            throw new IllegalArgumentException("Enter valid product purchase count");
        }

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product with given product id not found"));
        Category category = categoryRepo.findById(product.getCategory().getCategoryName())
                .orElseThrow(() -> new RuntimeException("Invalid Product Category"));

        // validating product stocks and if there is insufficient stocks then will send message to Admin
        if(product.getStockQuantity()<orderRequest.getProductCount()){
            if(product.getStockQuantity()<=5){
                sendThreshHoldMessage(product.getProductId(),product.getName(),product.getStockQuantity());
            }
            throw new RuntimeException("This product is out of stock");
        }

        //Generating Bill with GST
        BillingData finalBill = new BillingData(orderRequest.getProductCount(),category.getGstPercentage(),product.getPrice());

        //Gets the payment status
        boolean paymentStatus = getPaymentStatus();

        // if payment gets successful then we will place order and send message to buyer for successful order placement and will also update the stocks.
        if(paymentStatus){
            //due to successful payment, add order in order table
            Orders newOrder = new Orders(orderRequest.getName(),orderRequest.getContactNo(),product,orderRequest.getProductCount());
            ordersRepo.save(newOrder);

            //due to successful payment, update product stock quantity
            int updatedStock = updateProductStock(product,orderRequest.getProductCount());

            //check for product threshold
            if(updatedStock<=5){
                sendThreshHoldMessage(product.getProductId(),product.getName(),updatedStock);
            }

            //send order successful message to customer
            String toPhone = orderRequest.getContactNo();

            String message = generateSuccessfulMessage(
                    finalBill.getProductBasePrice(),
                    finalBill.getGstPercentage(),
                    finalBill.getGstAmount(),
                    finalBill.getPerProductFinalPrice(),
                    finalBill.getProductCount(),
                    finalBill.getFinalPrice()
            );
            sendSms(toPhone, message,"whatsapp:");
            return new UserResponse<>(finalBill,"Order Placed Successfully",true);
        }
        else{
            //send order failure message to customer
            String toPhone = orderRequest.getContactNo();
            String message = "❌ Your order failed due to payment issues. Please try again.";
            sendSms(toPhone, message,"whatsapp:");
            return new UserResponse<>(null,"Payment Failed",false);
        }
    }

    private void sendSms(String toPhone, String messageBody,String messageType) {
        String fromNumber;
        if(messageType.isBlank()) {
            fromNumber=twilioConfigs.getPhoneSms();
        }
        else{
            fromNumber=twilioConfigs.getPhoneWhatsapp();
        }
        Twilio.init(twilioConfigs.getAccountSid(), twilioConfigs.getAuthToken());
        Message.creator(
                new PhoneNumber(messageType+toPhone),
                new PhoneNumber(messageType+fromNumber),
                messageBody
        ).create();
    }

    // returns random boolean value
    private boolean getPaymentStatus(){
        Random random = new Random();
        return random.nextBoolean();
    }

    private int updateProductStock(Product productData,int productCount){
        productData.setStockQuantity(productData.getStockQuantity()-productCount);
        productRepo.save(productData);
        return productData.getStockQuantity();
    }

    private String generateSuccessfulMessage(
            BigDecimal productBasePrice,
            int productGSTPercentage,
            BigDecimal gstAmount,
            BigDecimal perProductFinalPrice,
            int productCount,
            BigDecimal finalPrice
    ){
        return String.format(
                "✅ Order placed successfully!\nProduct Base Price : ₹%.2f\nProduct GST Percentage : %d\nGST Amount: ₹%.2f\nPer Product Final Price : %.2f\nProduct Count : %d\nTotal: ₹%.2f\nThank you!",
                productBasePrice,
                productGSTPercentage,
                gstAmount,
                perProductFinalPrice,
                productCount,
                finalPrice
        );
    }

    public void sendThreshHoldMessage(int productId,String productName,int currentStock){
        String adminNumber = "+919586049357";
        String messageBody = String.format(
                "⚠️ Stock Alert!\nProduct ID: %d\nName: %s\nCurrent Stock: %d\nPlease restock soon to avoid order disruption.",
                productId,
                productName,
                currentStock
        );
        sendSms(adminNumber,messageBody,"");
    }
}
