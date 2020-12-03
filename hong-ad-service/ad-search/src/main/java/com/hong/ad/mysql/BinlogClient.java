package com.hong.ad.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.hong.ad.mysql.listener.AggregationListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  11:54 下午 2020/6/4
 */
@Slf4j
@Component
public class BinlogClient {

    private BinaryLogClient client;
    private final BinlogConfig config;
    private final AggregationListener listener;

    @Autowired
    public BinlogClient (AggregationListener listener, BinlogConfig config) {
        this.listener = listener;
        this.config = config;
    }

    public void connect () {
        new Thread(() -> {
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
            if (!StringUtils.isEmpty(config.getBinglogName()) && !config.getPosition().equals(-1)) {
                client.setBinlogFilename(config.getBinglogName());
                // 指定具体的位置开始监听
                client.setBinlogPosition(config.getPosition());
            }
            try {
                log.info("connecting to mysql start");
                client.connect();
                log.info("connecting to mysql done");
            } catch (Exception e) {
                e.getStackTrace();
                //log.info("connection mysq error {}", e.getMessage());
            }
        }).start();
    }
}
