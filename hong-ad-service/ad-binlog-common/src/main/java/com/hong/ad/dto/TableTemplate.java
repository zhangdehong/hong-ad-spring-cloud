package com.hong.ad.dto;

import com.hong.ad.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  10:37 下午 2020/4/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableTemplate {

    private String tableName;
    private String level;

    /**
     * 操作类型--列名
     */
    private Map<OpType, List<String>> opTypeFiledSetMap = new HashMap<>();
    /**
     * 字段索引--字段名的映射
     */
    private Map<Integer, String> posMap = new HashMap<>();
}
