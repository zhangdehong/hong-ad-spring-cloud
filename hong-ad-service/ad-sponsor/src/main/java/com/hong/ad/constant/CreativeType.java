package com.hong.ad.constant;

import lombok.Getter;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  9:09 PM 2019/11/19
 */
@Getter
public enum CreativeType {

    IMAGE(1, "图片"),
    VIDEO(2, "视频"),
    TEXT(3, "文本");

    private Integer type;

    private String desc;

    CreativeType (Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
