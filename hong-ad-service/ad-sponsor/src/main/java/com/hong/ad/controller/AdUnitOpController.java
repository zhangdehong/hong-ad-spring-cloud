package com.hong.ad.controller;

import com.alibaba.fastjson.JSON;
import com.hong.ad.exception.AdException;
import com.hong.ad.service.IAdUnitService;
import com.hong.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  7:13 PM 2019/11/25
 */
@Slf4j
@RestController
public class AdUnitOpController {


    private final IAdUnitService unitService;

    @Autowired
    public AdUnitOpController (IAdUnitService unitService) {this.unitService = unitService;}

    /**
     * 创建推广单元
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/create/unit")
    public AdUnitResponse createUnit (@RequestBody AdUnitRequest request) throws AdException {

        log.info("ad-sponsor:CreateUnit -> {}", JSON.toJSONString(request));
        return unitService.createAdUnit(request);
    }

    /**
     * 创建推广单元的关键词限制
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/create/unitKeyword")
    public AdUnitKewordResponse createUnitKeyword (@RequestBody AdUnitKewordRequest request) throws AdException {
        log.info("ad-sponsor:createUnitKeyword ->{}", JSON.toJSONString(request));
        return unitService.createUnitKeword(request);
    }

    /**
     * 推广单元兴趣
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/create/unitIt")
    public AdUnitItResponse createUnitIt (@RequestBody AdUnitItRequest request) throws AdException {
        log.info("ad-sponsor:createUnit -> {}", JSON.toJSONString(request));
        return unitService.createUnitIt(request);
    }

    /**
     * 创建推广单元限制
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/create/unitDistrict")
    public AdUnitDistrictResponse createUnitDistrict (@RequestBody AdUnitDistrictRequest request) throws AdException {
        log.info("ad-sponsor:createUnitDistrict -> {}", JSON.toJSONString(request));
        return unitService.createUnitDistrict(request);
    }

    /**
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/create/creativeUnit")
    public CreativeUnitResponse createCreativeUnit (@RequestBody CreativeUnitRequest request) throws AdException {
        log.info("ad-sponsor:createCreativeUnit -> {}", JSON.toJSONString(request));
        return unitService.createCreativeUnit(request);
    }
}
