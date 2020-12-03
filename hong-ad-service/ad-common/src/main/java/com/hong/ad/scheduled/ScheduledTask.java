package com.hong.ad.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  6:08 PM 2019/11/18
 */
@Component
public class ScheduledTask {

    //@Scheduled(fixedRate = 5000)            // 上一次执行开始5秒之后再执行
    //@Scheduled(fixedDelay = 5000)           // 上一次执行完毕之后5秒再执行
    //@Scheduled(cron = "*/5 * * * * *")      // 通过crontab定义执行规则

    //@Scheduled(fixedRate = 1000)
    //public void helloSpringBoot(){
    //    System.out.println("hello springBoot");
    //}
}
