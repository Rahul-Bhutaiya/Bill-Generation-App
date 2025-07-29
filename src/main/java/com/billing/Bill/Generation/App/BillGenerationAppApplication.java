package com.billing.Bill.Generation.App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BillGenerationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillGenerationAppApplication.class, args);
	}

}
