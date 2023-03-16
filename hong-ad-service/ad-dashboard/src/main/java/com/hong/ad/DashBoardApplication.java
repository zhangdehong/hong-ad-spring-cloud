package com.hong.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  12:44 上午 2020/7/20
 */
@EnableEurekaClient
@SpringBootApplication
@EnableHystrixDashboard
public class DashBoardApplication {

    public static void main (String[] args) {
        SpringApplication.run(DashBoardApplication.class, args);
    }
}
