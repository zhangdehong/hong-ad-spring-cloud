package com.hong.ad.client;

import com.hong.ad.client.vo.AdPlan;
import com.hong.ad.client.vo.AdPlanGetRequest;
import com.hong.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  3:05 PM 2020/1/28
 */
@Component
public class SponsorClientHystrix implements SponsorClient{


    @Override
    public CommonResponse<List<AdPlan>> getAdPlans (AdPlanGetRequest request) {
        return new CommonResponse<>(-1,"client-ad-sponsor error");
    }
}
