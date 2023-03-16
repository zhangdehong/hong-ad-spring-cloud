package com.hong.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  4:54 PM 2019/11/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitKewordRequest {

    private List<UnitKeword> unitKewords;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnitKeword{
        private Long unitId;
        private String keword;
    }
}
