package com.billing.Bill.Generation.App.configs;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio")
public class TwilioConfigs {
    private String accountSid;

    private String authToken;

    private String phoneWhatsapp;

    private String phoneSms;

    // Getters and setters

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPhoneWhatsapp() {
        return phoneWhatsapp;
    }

    public void setPhoneWhatsapp(String phoneWhatsapp) {
        this.phoneWhatsapp = phoneWhatsapp;
    }

    public String getPhoneSms() {
        return phoneSms;
    }

    public void setPhoneSms(String phoneSms) {
        this.phoneSms = phoneSms;
    }
}