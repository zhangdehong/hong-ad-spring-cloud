package com.hong.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 纬度限制
 *
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  11:20 下午 2020/6/8
 */
@Data
public class DistrictFeature {

    private List<ProvinceAndCity> districts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProvinceAndCity {

        private String province;
        private String city;
    }
}
