package com.hong.ad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  10:21 下午 2020/4/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTable {

    /**
     * 表名字
     */
    private String tableName;
    /**
     * 层级
     */
    private Integer level;

    private List<Column> insert;
    private List<Column> update;
    private List<Column> delete;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Column {

        private String column;
    }
}
