package com.hong.ad.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 转换为在检索系统中定义的Java bean
 *
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  10:01 下午 2020/5/11
 */
@Data
public class BinlogRowData {

    private TableTemplate table;

    private EventType eventType;

    private List<Map<String, String>> before;

    private List<Map<String, String>> after;
}
