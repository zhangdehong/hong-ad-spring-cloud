package com.hong.ad.index;

import lombok.Getter;

/**
 * 索引层级枚举类
 *
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  9:10 下午 2020/6/5
 */
@Getter
public enum DataLevel {
    LEVEL2("2", "level 2"),
    LEVEL3("3", "level 3"),
    LEVEL4("4", "level 4");

    private String level;
    private String desc;

    DataLevel (String level, String desc) {
        this.level = level;
        this.desc = desc;
    }
}
