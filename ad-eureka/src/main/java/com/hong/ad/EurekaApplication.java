package com.hong.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  11:21 PM 2019/11/15
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication {

    public static void main (String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
