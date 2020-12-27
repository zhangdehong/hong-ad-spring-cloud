package com.hong.ad.dto;

import com.hong.ad.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  11:58 下午 2020/5/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRowData {

    private String tableName;
    private String level;
    private OpType opType;
    private List<Map<String,String>> failedValueMap = new ArrayList<>();
}
