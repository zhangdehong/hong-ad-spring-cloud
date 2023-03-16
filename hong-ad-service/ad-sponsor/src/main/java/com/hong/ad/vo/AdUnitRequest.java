package com.hong.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  4:10 PM 2019/11/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitRequest {

    private Long planId;
    private String unitName;

    private Integer positionType;
    private Long budget;

    public boolean createValidate(){
        return null != planId && !StringUtils.isEmpty(unitName)
                && null != positionType
                && null != budget;
    }

}
