package com.hong.ad.vo;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  1:11 PM 2019/11/24
 */
@Data
public class AdPlanGetRequest {

    private Long userId;
    private List<Long> ids;

    public boolean validate(){
        return userId != null && !CollectionUtils.isEmpty(ids);
    }
}
