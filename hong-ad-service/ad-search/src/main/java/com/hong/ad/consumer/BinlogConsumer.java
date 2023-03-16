package com.hong.ad.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hong.ad.dto.MySqlRowData;
import com.hong.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: zhang-dehong
 * @Describe: TODO
 * @Date Create by  11:03 下午 2020/12/24
 */
@Slf4j
@Component
public class BinlogConsumer {

    private final ISender sender;

    @Autowired
    public BinlogConsumer (ISender sender) {this.sender = sender;}

    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMysqlRowData (ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        // 如果不为空 就消费
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MySqlRowData rowData = JSON.parseObject(
                    message.toString(), MySqlRowData.class
            );
            log.info("kafka processMysqlRowData: {}", JSON.toJSONString(
                    rowData,
                    SerializerFeature.PrettyFormat,
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat
            ));
            // 实现广告数据索引的更新
            sender.sender(rowData);
        }
    }
}
