package com.hong.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  11:48 PM 2020/1/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlan {

    private Long id;
    private Long userId;
    private String planName;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;
    private Date createTime;
    private Date updateTime;

}
