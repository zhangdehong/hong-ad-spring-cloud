package com.hong.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  11:28 PM 2019/11/18
 */
@EnableFeignClients
@EnableCircuitBreaker
// @EnableEurekaClient
@SpringBootApplication
public class SponsorApplication {

    public static void main (String[] args) {
        SpringApplication.run(SponsorApplication.class,args);
    }
}
