package com.hong.ad.constant;

import lombok.Getter;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  12:05 AM 2019/11/19
 */
@Getter
public enum CommonStatus {

    VALID(1,"有效状态"),
    INVALID(0,"无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status,String desc){
        this.status = status;
        this.desc = desc;
    }
}
