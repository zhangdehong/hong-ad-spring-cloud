package com.hong.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 广告位
 *
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  10:46 下午 2020/6/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdSlot {

    // 广告位编码
    private String adSlotCode;
    // 流量类型
    private Integer positionType;
    // 广告位的宽高
    private Integer width;
    private Integer height;

    // 广告位物料类型 图片视频等
    private List<Integer> type;
    // 最低出价
    private Integer minCpm;
}
