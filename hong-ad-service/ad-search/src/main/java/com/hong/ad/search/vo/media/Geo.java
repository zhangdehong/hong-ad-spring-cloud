package com.hong.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  11:09 下午 2020/6/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {

    // 经度
    private Float latitude;
    // 纬度
    private Float longitude;

    // 城市
    private String city;
    // 省份
    private String province;
}
