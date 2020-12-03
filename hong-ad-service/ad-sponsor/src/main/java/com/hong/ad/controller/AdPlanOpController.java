package com.hong.ad.controller;

import com.alibaba.fastjson.JSON;
import com.hong.ad.entity.AdPlan;
import com.hong.ad.exception.AdException;
import com.hong.ad.service.IAdPlanService;
import com.hong.ad.vo.AdPlanGetRequest;
import com.hong.ad.vo.AdPlanRequest;
import com.hong.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  3:54 PM 2019/11/25
 */
@Slf4j
@RestController
public class AdPlanOpController {

    private final IAdPlanService adPlanService;

    @Autowired
    public AdPlanOpController (IAdPlanService adPlanService) {this.adPlanService = adPlanService;}

    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan (@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor:createAdPlan -> {}", JSON.toJSONString(request));
        return adPlanService.createAdPlan(request);
    }

    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlanByIds (@RequestBody AdPlanGetRequest request) throws AdException {
        log.info("ad-sponsor:getAdPlanByIds -> {}", JSON.toJSONString(request));
        return adPlanService.getAdPlanByIds(request);
    }

    @PostMapping("/update/adPlan")
    public AdPlanResponse updateAdPlan (@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor:updateAdPlan -> {}", JSON.toJSONString(request));
        return adPlanService.updateAdPlan(request);
    }

    @DeleteMapping("/delete/adPlan")
    public void deleteAdPlan (@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor:deleteAdPlan -> {}", JSON.toJSONString(request));
        adPlanService.deleteAdPlan(request);
    }
}
