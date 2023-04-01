package com.hong.ad.controller;

import com.alibaba.fastjson.JSON;
import com.hong.ad.annotation.IgnoreResponseAdvice;
import com.hong.ad.client.SponsorClient;
import com.hong.ad.client.vo.AdPlan;
import com.hong.ad.client.vo.AdPlanGetRequest;
import com.hong.ad.search.ISearch;
import com.hong.ad.search.vo.SearchRequest;
import com.hong.ad.search.vo.SearchResponse;
import com.hong.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  11:55 PM 2020/1/8
 */
@Slf4j
@RestController
public class SearchController {

    private final ISearch search;

    private final RestTemplate restTemplate;

    private final SponsorClient sponsorClient;

    @Autowired
    public SearchController (RestTemplate restTemplate,
                             SponsorClient sponsorClient, ISearch search) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
        this.search = search;
    }

    @PostMapping("/fetchAds")
    public SearchResponse fetchAds (@RequestBody SearchRequest request) {
        log.info("ad-search: fetchAds -> {}", JSON.toJSONString(request));
        return search.fetchAds(request);
    }

    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRebbon (@RequestBody AdPlanGetRequest request) {
        log.info("ad-search:getAdPlansByRibbon -> {}", JSON.toJSONString(request));
        return restTemplate.postForEntity(
                "http://ad-sponsor-client/ad-sponsor/get/adPlan",
                request,
                CommonResponse.class
        ).getBody();
    }

    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlans (@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlans -> {}", JSON.toJSONString(request));
        CommonResponse<List<AdPlan>> adPlans = sponsorClient.getAdPlans(request);
        return adPlans;
    }
}
