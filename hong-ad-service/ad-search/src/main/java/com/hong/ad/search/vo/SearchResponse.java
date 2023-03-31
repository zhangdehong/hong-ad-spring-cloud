package com.hong.ad.search.vo;

import com.hong.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  9:41 下午 2020/6/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {

    // 广告位信息和创意信息的 映射关系
    public Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    /**
     * 广告创意
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Creative {

        // 广告id
        private Long adId;
        // 广告地址
        private String adUrl;
        // 广告宽高
        private Integer width;
        private Integer height;
        // 广告类型
        private Integer type;
        // 子类型
        private Integer materialType;

        // 展示监测URL
        private List<String> showMonitorUrl = Arrays.asList("www.hong.com", "www.hong.com");
        // 点击监测URL
        private List<String> clickMonitorUrl = Arrays.asList("www.hong.com", "www.hong.com");
    }

    /**
     * 装换为媒体方的广告创意数据
     *
     * @param object
     * @return Creative
     */
    public static Creative convert (CreativeObject object) {
        Creative creative = new Creative();
        creative.setAdId(object.getAdId());
        creative.setAdUrl(object.getAdUrl());
        creative.setHeight(object.getHeight());
        creative.setWidth(object.getWidth());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());
        return creative;
    }
}
