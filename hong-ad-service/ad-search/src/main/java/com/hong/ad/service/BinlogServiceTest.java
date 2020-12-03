package com.hong.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  12:43 AM 2020/4/17
 */
@Slf4j
public class BinlogServiceTest {

    public static void main (String[] args) throws IOException {
        BinaryLogClient client = new BinaryLogClient(
                "127.0.0.1",
                3306,
                "root",
                "hong#0618"
        );
        // 从那个binlog文件开始监听   不设置的话从最后一个最新的位置开始监听
        //client.setBinlogFilename();
        // 指定监听的位置   不指定就从最新的位置开始监听
        //client.setBinlogPosition();
        // 注册到一个事件的监听器  监听mysql数据的变化
        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof UpdateRowsEventData) {
                log.info("update--start--");
                log.info("update data--->{}", data);
            } else if (data instanceof WriteRowsEventData) {
                log.info("write--start--");
                log.info("write  data--->{}", data);
            } else if (data instanceof DeleteRowsEventData) {
                log.info("delete--start--");
                log.info("delete data--->{}", data);
            }
        });
        client.connect();
    }
}
