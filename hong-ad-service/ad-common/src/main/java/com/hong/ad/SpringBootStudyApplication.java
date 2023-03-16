package com.hong.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  5:29 PM 2019/11/18
 */
@EnableScheduling
@SpringBootApplication
public class SpringBootStudyApplication {

    /**
     * <h2>应用入口类</h2>
     *
     * @param args
     */
    public static void main (String[] args) {
        SpringApplication.run(SpringBootStudyApplication.class, args);
    }
}
