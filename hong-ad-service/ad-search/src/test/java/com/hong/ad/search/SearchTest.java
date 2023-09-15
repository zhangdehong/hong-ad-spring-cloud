package com.hong.ad.search;

import com.alibaba.fastjson.JSON;
import com.hong.ad.Application;
import com.hong.ad.search.vo.SearchRequest;
import com.hong.ad.search.vo.feature.DistrictFeature;
import com.hong.ad.search.vo.feature.FeatureRelation;
import com.hong.ad.search.vo.feature.ItFeature;
import com.hong.ad.search.vo.feature.KeywordFeature;
import com.hong.ad.search.vo.media.AdSlot;
import com.hong.ad.search.vo.media.App;
import com.hong.ad.search.vo.media.Device;
import com.hong.ad.search.vo.media.Geo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create by  12:34 2023/4/5
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {

    @Autowired
    private ISearch search;

    @Test
    public void testFetchAds () {
        SearchRequest request = new SearchRequest();
        request.setMediaId("dh-ad");

        request.setRequestInfo(new SearchRequest.RequestInfo(
                "aaa",
                Collections.singletonList(new AdSlot(
                        "ad-x", 1, 1080, 720, Arrays.asList(1, 2, 3), 1000
                )),
                buildExampleApp(),
                buildExampleGeo(),
                buildExampelDevice()
        ));
        request.setFeatureInfo(buildExampleFeatureInfo(
                Arrays.asList("宝马", "大众"),
                Collections.singletonList(
                        new DistrictFeature.ProvinceAndCity("安徽省", "合肥市")
                ),
                Arrays.asList("台球", "游泳"),
                FeatureRelation.OR
        ));
        System.out.println(JSON.toJSONString(request));
        System.out.println(JSON.toJSONString(search.fetchAds(request)));
    }

    private App buildExampleApp () {
        return new App("dh", "dh", "hong.com", "video");
    }

    private Geo buildExampleGeo () {
        return new Geo(
                100.28f, 88.61f, "北京", "北京"
        );
    }

    private Device buildExampelDevice () {
        return new Device(
                "iphone",
                "0XXXXXXX",
                "9.9.9.9",
                "14 ProMax",
                "1080 720",
                "1080 720",
                "888888888"
        );
    }

    private SearchRequest.FeatureInfo buildExampleFeatureInfo (
            List<String> keywords,
            List<DistrictFeature.ProvinceAndCity> provinceAndCities,
            List<String> its,
            FeatureRelation relation
    ) {

        return new SearchRequest.FeatureInfo(
                new KeywordFeature(keywords),
                new DistrictFeature(provinceAndCities),
                new ItFeature(its),
                relation
        );
    }
}
