package com.hong.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  1:03 PM 2019/11/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlanRequest {

    private Long id;
    private Long userId;
    private String planNname;
    private String startDate;
    private String endDate;

    public boolean createValidate () {
        return userId != null && StringUtils.isEmpty(planNname)
                && StringUtils.isEmpty(startDate)
                && StringUtils.isEmpty(endDate);
    }

    public boolean updateValidate () {
        return id != null && userId != null;
    }

    public boolean deleteValidate () {
        return id != null;
    }
}
