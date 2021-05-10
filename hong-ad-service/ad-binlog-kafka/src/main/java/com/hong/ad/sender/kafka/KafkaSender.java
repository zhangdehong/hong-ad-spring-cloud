package com.hong.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hong.ad.dto.MySqlRowData;
import com.hong.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  6:23 下午 2020/6/7
 */
@Slf4j
@Component
public class KafkaSender implements ISender {

    @Value("${adconf.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaSender (KafkaTemplate<String, String> kafkaTemplate) {this.kafkaTemplate = kafkaTemplate;}

    @Override
    public void sender (MySqlRowData rowData) {
        log.info("binlog kafka service send MysqlRowData. ->{}", JSON.toJSONString(
                rowData,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat
        ));
        kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }

    // @KafkaListener(topics = {"ad-search-mysql-data"},groupId = "ad-search")
    // public void processMySqlRowData (ConsumerRecord<?, ?> record) {
    //     Optional<?> kafkaMessage = Optional.ofNullable(record.value());
    //     if (kafkaMessage.isPresent()) {
    //         Object message = kafkaMessage.get();
    //         MySqlRowData rowData = JSON.parseObject(message.toString(), MySqlRowData.class);
    //         log.info("kafka processMysqlRowData {}", JSON.toJSONString(rowData));
    //     }
    // }
}
