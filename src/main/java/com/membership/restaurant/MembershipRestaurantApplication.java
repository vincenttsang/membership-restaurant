package com.membership.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MembershipRestaurantApplication {

    public static void main(String[] args) {
        SpringApplication.run(MembershipRestaurantApplication.class, args);
    }

}
