package com.hong.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * <h1>Binlog kafka 启动程序</h1>
 * @Author: zhang-dehong
 * @Describe: TODO
 * @Date Create by  9:54 下午 2020/12/22
 */
@EnableEurekaClient
@SpringBootApplication
public class BinlogKafkaApplication {

    public static void main (String[] args) {
        SpringApplication.run(BinlogKafkaApplication.class, args);
    }
}
