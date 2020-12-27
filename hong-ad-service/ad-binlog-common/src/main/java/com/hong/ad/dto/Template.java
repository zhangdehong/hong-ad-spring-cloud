package com.hong.ad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  10:33 下午 2020/4/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Template {

    private String database;
    private List<JsonTable> tableList;
}
