package com.hong.ad.search.impl;

import com.alibaba.fastjson.JSON;
import com.hong.ad.index.CommonStatus;
import com.hong.ad.index.DataTable;
import com.hong.ad.index.adunit.AdUnitIndex;
import com.hong.ad.index.adunit.AdUnitObject;
import com.hong.ad.index.creative.CreativeIndex;
import com.hong.ad.index.creative.CreativeObject;
import com.hong.ad.index.creativeunit.CreativeUnitIndex;
import com.hong.ad.index.district.UnitDistrictIndex;
import com.hong.ad.index.interest.UnitItIndex;
import com.hong.ad.index.keyword.UnitKeyWordIndex;
import com.hong.ad.search.ISearch;
import com.hong.ad.search.vo.SearchRequest;
import com.hong.ad.search.vo.SearchResponse;
import com.hong.ad.search.vo.feature.DistrictFeature;
import com.hong.ad.search.vo.feature.FeatureRelation;
import com.hong.ad.search.vo.feature.ItFeature;
import com.hong.ad.search.vo.feature.KeywordFeature;
import com.hong.ad.search.vo.media.AdSlot;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  10:01 上午 2020/6/11
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {

    private SearchResponse fallBack (SearchRequest request, Throwable e) {
        return null;
    }

    /**
     * HystrixCommand  实现错误的回退，方法必须定义在当前的类中
     *
     * @param request
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "fallBack")
    public SearchResponse fetchAds (SearchRequest request) {
        // 请求广告位信息
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();
        // 三个feature
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();
        FeatureRelation relation = request.getFeatureInfo().getRelation();
        // 构造响应对象
        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();
        for (AdSlot slot : adSlots) {
            Set<Long> targetUnitIdSet;
            // 根据流量类型 获取初始的 AdUnit
            Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(slot.getPositionType());
            if (relation == FeatureRelation.AND) {
                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterItTagFeature(adUnitIdSet, itFeature);
                // 取出来  如果是or
                targetUnitIdSet = adUnitIdSet;
            } else {
                targetUnitIdSet = getOrRelation(
                        adUnitIdSet,
                        keywordFeature,
                        districtFeature,
                        itFeature
                );
            }
            List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);
            filterAdUnitAdPlanStatus(unitObjects, CommonStatus.VALID);
            // 获取到关联的创意id
            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            // 通过创意id获取到对应的创意对象
            List<CreativeObject> creativeObjects = DataTable.of(CreativeIndex.class).fetch(adIds);
            // 通过 AdSlot 对 creativeObject 的过滤
            filterCreativeByAdSlot(creativeObjects, slot.getWidth(), slot.getHeight(), slot.getType());
            adSlot2Ads.put(slot.getAdSlotCode(), buildCreativeResponse(creativeObjects));
        }
        log.info("fetchAds request : {} / response : {}", JSON.toJSONString(request), JSON.toJSONString(response));
        return response;
    }

    private Set<Long> getOrRelation (Set<Long> adUnitIdSet, KeywordFeature keywordFeature,
                                     DistrictFeature districtFeature,
                                     ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIdSet)) {
            return Collections.emptySet();
        }
        // 保存个副本
        Set<Long> keywordUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> itUnitIdSet = new HashSet<>(adUnitIdSet);
        // 进行过滤
        filterKeywordFeature(keywordUnitIdSet, keywordFeature);
        filterDistrictFeature(districtUnitIdSet, districtFeature);
        filterItTagFeature(itUnitIdSet, itFeature);
        // 实现并集合
        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordUnitIdSet, districtUnitIdSet),
                        itUnitIdSet
                )
        );
    }

    /**
     * 过滤关键词 feature
     *
     * @param adUnitIds
     * @param keywordFeature
     */
    private void filterKeywordFeature (Collection<Long> adUnitIds, KeywordFeature keywordFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        // UnitKeyWordIndex keyWordIndex = DataTable.of(UnitKeyWordIndex.class);
        // 判断给定的条件是否通过  如果不通过就remove
        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            CollectionUtils.filter(
                    adUnitIds, // 需要过滤的集合
                    // 如果adUnitId 通过不过滤，如果不通过  从adUnitIds 中移除掉
                    adUnitId -> DataTable.of(UnitKeyWordIndex.class)
                            .match(adUnitId, keywordFeature.getKeywords())
            );
        }
    }

    /**
     * 根据地域feature过滤方法
     *
     * @param adUnitIds
     * @param districtFeature
     */
    private void filterDistrictFeature (Collection<Long> adUnitIds, DistrictFeature districtFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> DataTable.of(UnitDistrictIndex.class)
                            .match(adUnitId, districtFeature.getDistricts())
            );
        }
    }

    /**
     * 根据兴趣feature过滤
     *
     * @param adUnitIds
     * @param itFeature
     */
    private void filterItTagFeature (Collection<Long> adUnitIds, ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> DataTable.of(UnitItIndex.class)
                            .match(adUnitId, itFeature.getIts())
            );
        }
    }

    /**
     * 根据推广单元以及推广计划的状态 实现对unitObjects的过滤
     *
     * @param unitObjects
     * @param status
     */
    private void filterAdUnitAdPlanStatus (List<AdUnitObject> unitObjects, CommonStatus status) {
        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }
        CollectionUtils.filter(
                unitObjects,
                object -> object.getUnitStatus().equals(status.getStatus())
                        && object.getAdPlanObject().getPlanStatus().equals(status.getStatus())
        );
    }

    private void filterCreativeByAdSlot (List<CreativeObject> creatives, Integer width
            , Integer height, List<Integer> type) {
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }
        CollectionUtils.filter(
                creatives,
                creative -> creative.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                        && creative.getWidth().equals(width)
                        && creative.getHeight().equals(height)
                        && type.contains(creative.getMaterialType())
        );
    }

    /**
     * 构造创意响应
     *
     * @param creatives
     * @return
     */
    private List<SearchResponse.Creative> buildCreativeResponse (List<CreativeObject> creatives) {
        if (CollectionUtils.isEmpty(creatives)) {
            return Collections.emptyList();
        }
        // 随机获取一个
        CreativeObject randomObject = creatives.get(
                Math.abs(new Random().nextInt()) % creatives.size()
        );
        //  随机返回一个创意对象
        return Collections.singletonList(
                SearchResponse.convert(randomObject)
        );
    }
}
