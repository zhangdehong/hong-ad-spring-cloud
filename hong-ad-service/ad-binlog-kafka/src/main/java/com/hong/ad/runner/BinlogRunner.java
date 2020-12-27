package com.hong.ad.runner;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.hong.ad.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  12:08 上午 2020/6/5
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    private final BinlogClient client;

    @Autowired
    public BinlogRunner (BinlogClient client) {this.client = client;}

    @Override
    public void run (String... args) throws Exception {
        log.info("coming in BinlogRunner...");
        client.connect();
    }
}
