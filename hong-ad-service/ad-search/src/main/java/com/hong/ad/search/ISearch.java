package com.hong.ad.search;

import com.hong.ad.search.vo.SearchRequest;
import com.hong.ad.search.vo.SearchResponse;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  10:35 下午 2020/6/7
 */
public interface ISearch {

    /**
     * 根据匹配信息 实现推广单元的再筛选
     *
     * @param request
     * @return
     */
    SearchResponse fetchAds (SearchRequest request);
}
