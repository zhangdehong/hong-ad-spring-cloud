package com.hong.ad.index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地域对象
 *
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  11:29 AM 2020/2/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDistrictObject {

    private Long unitId;
    private String province;
    private String city;

    // <String Set<Long>> Set<Long> unitId推广单元集合
    // province-city  省市构造成一个key
}
