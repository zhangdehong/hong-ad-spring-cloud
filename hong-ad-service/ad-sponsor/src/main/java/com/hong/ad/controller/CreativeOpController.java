package com.hong.ad.controller;

import com.alibaba.fastjson.JSON;
import com.hong.ad.exception.AdException;
import com.hong.ad.service.ICreativeService;
import com.hong.ad.vo.CreativeRequest;
import com.hong.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  10:17 PM 2020/1/1
 */
@Slf4j
@RestController
public class CreativeOpController {

    private final ICreativeService creativeService;

    @Autowired
    public CreativeOpController (ICreativeService creativeService) {this.creativeService = creativeService;}

    @PostMapping("/create/creative")
    public CreativeResponse createCreative (@RequestBody CreativeRequest request) throws AdException {
        log.info("ad-sponsor:createCreative -> {}", JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }
}
