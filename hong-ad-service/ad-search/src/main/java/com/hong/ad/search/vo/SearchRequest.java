package com.hong.ad.search.vo;

import com.hong.ad.search.vo.feature.DistrictFeature;
import com.hong.ad.search.vo.feature.FeatureRelation;
import com.hong.ad.search.vo.feature.ItFeature;
import com.hong.ad.search.vo.feature.KeywordFeature;

import com.hong.ad.search.vo.media.AdSlot;
import com.hong.ad.search.vo.media.App;
import com.hong.ad.search.vo.media.Device;
import com.hong.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  10:37 下午 2020/6/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {

    // 媒体请求方的唯一标识
    private String mediaId;
    // 请求基本信息
    private RequestInfo requestInfo;

    // 匹配信息
    private FeatureInfo featureInfo;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestInfo {

        private String requestId;
        // 广告位信息
        private List<AdSlot> adSlots;
        // app信息
        private App app;
        // 地理位置信息
        private Geo geo;
        // 设备信息
        private Device device;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeatureInfo {

        // 关键词
        private KeywordFeature keywordFeature;
        //
        private DistrictFeature districtFeature;
        // 兴趣
        private ItFeature itFeature;
        // 默认所有的feature都满足 才匹配
        private FeatureRelation relation = FeatureRelation.AND;
    }
}
