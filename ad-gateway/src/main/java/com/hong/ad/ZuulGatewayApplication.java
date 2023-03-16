package com.hong.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Zuul网关启动类
 *
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  3:58 AM 2019/11/17
 */
@EnableZuulProxy
@SpringCloudApplication
public class ZuulGatewayApplication {

    public static void main (String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);
    }
}
