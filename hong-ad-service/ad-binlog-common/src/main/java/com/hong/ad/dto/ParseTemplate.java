package com.hong.ad.dto;

import com.hong.ad.constant.OpType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  12:05 上午 2020/4/24
 */
@Data
public class ParseTemplate {

    private String database;
    /**
     * 表名称--
     */
    private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

    public static ParseTemplate parse (Template _template) {
        ParseTemplate template = new ParseTemplate();
        template.setDatabase(_template.getDatabase());
        for (JsonTable table : _template.getTableList()) {
            String tableName = table.getTableName();
            Integer level = table.getLevel();
            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(tableName);
            tableTemplate.setLevel(level.toString());
            template.tableTemplateMap.put(tableName, tableTemplate);

            // 遍历操作类型对应的列
            Map<OpType, List<String>> opTypeFiledSetMap = tableTemplate.getOpTypeFiledSetMap();
            for (JsonTable.Column column : table.getInsert()) {
                getAndCreateIfNeed(
                        OpType.ADD,
                        opTypeFiledSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getUpdate()) {
                getAndCreateIfNeed(
                        OpType.UPDATE,
                        opTypeFiledSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getDelete()) {
                getAndCreateIfNeed(
                        OpType.DELETE,
                        opTypeFiledSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
        }
        return template;
    }

    private static <T, R> R getAndCreateIfNeed (T key, Map<T, R> map, Supplier<R> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
