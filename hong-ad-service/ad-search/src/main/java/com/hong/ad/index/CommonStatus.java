package com.hong.ad.index;

import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * 推广计划的状态
 *
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  1:52 上午 2020/6/14
 */
public enum CommonStatus {

    VALID(1, "有效状态"),
    INVALID(0, "无效状态");

    @Getter
    private Integer status;
    @Getter
    private String desc;

    CommonStatus (Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
