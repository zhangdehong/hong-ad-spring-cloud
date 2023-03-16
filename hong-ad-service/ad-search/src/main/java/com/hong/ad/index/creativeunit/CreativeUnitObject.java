package com.hong.ad.index.creativeunit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  5:02 PM 2020/2/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeUnitObject {

    // 广告id
    private Long adId;
    // 推广单元id
    private Long unitId;

    // adId-unitId
}
