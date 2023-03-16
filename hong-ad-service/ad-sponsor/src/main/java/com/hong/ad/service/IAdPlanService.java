package com.hong.ad.service;

import com.hong.ad.entity.AdPlan;
import com.hong.ad.exception.AdException;
import com.hong.ad.vo.AdPlanGetRequest;
import com.hong.ad.vo.AdPlanRequest;
import com.hong.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  1:02 PM 2019/11/24
 */
public interface IAdPlanService {

    /**
     * 创建推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan (AdPlanRequest request) throws AdException;

    /**
     * 创建推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlanByIds (AdPlanGetRequest request) throws AdException;

    /**
     * 更新推广计划
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan (AdPlanRequest request) throws AdException;

    /**
     * <h2>删除推广计划</h2>
     *
     * @param request
     * @throws AdException
     */
    void deleteAdPlan (AdPlanRequest request) throws AdException;
}
