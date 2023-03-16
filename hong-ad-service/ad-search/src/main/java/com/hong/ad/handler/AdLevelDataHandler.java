package com.hong.ad.handler;

import com.alibaba.fastjson.JSON;
import com.hong.ad.dump.table.*;
import com.hong.ad.index.DataTable;
import com.hong.ad.index.IndexAware;
import com.hong.ad.index.adplan.AdPlanIndex;
import com.hong.ad.index.adplan.AdPlanObject;
import com.hong.ad.index.adunit.AdUnitIndex;
import com.hong.ad.index.adunit.AdUnitObject;
import com.hong.ad.index.creative.CreativeIndex;
import com.hong.ad.index.creative.CreativeObject;
import com.hong.ad.index.creativeunit.CreativeUnitIndex;
import com.hong.ad.index.creativeunit.CreativeUnitObject;
import com.hong.ad.index.district.UnitDistrictIndex;
import com.hong.ad.index.interest.UnitItIndex;
import com.hong.ad.index.keyword.UnitKeyWordIndex;
import com.hong.ad.constant.OpType;
import com.hong.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * 1.索引之间存在着层级的划分，也就是依赖关系的划分
 * 2.加载全量索引其实是增量索引"添加的"一种特殊实现
 *
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  8:33 AM 2020/2/25
 */
@Slf4j
public class AdLevelDataHandler {

    /**
     * 推广计划
     *
     * @param planTable
     * @param type
     */
    public static void handlerLevel2 (AdPlanTable planTable, OpType type) {
        AdPlanObject planObject = new AdPlanObject(
                planTable.getId(),
                planTable.getUserId(),
                planTable.getPlanStatus(),
                planTable.getStartDate(),
                planTable.getEndDate()
        );
        handlerBinlogEvent(
                DataTable.of(AdPlanIndex.class),
                planObject.getPlanId(),
                planObject,
                type
        );
    }

    /**
     * 创意纬度
     *
     * @param creativeTable
     * @param type
     */
    public static void handlerLevel2 (AdCreativeTable creativeTable, OpType type) {
        CreativeObject creativeObject = new CreativeObject(
                creativeTable.getAdId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getAdUrl()
        );
        handlerBinlogEvent(
                DataTable.of(CreativeIndex.class),
                creativeObject.getAdId(),
                creativeObject,
                type
        );
    }

    /**
     * 推广单元
     *
     * @param unitTable
     * @param type
     */
    public static void handlerLevel3 (AdUnitTable unitTable, OpType type) {
        AdPlanObject adPlanObject = DataTable.of(
                AdPlanIndex.class
        ).get(unitTable.getPlanId());
        if (null == adPlanObject) {
            log.error("handlerLevel3 found AdPlanObject error:{}", unitTable.getPlanId());
            return;
        }
        AdUnitObject unitObject = new AdUnitObject(
                unitTable.getUnitId(),
                unitTable.getUnitStatus(),
                unitTable.getPositionType(),
                unitTable.getPlanId(),
                adPlanObject
        );
        handlerBinlogEvent(
                DataTable.of(AdUnitIndex.class),
                unitTable.getUnitId(),
                unitObject,
                type
        );
    }

    /**
     * 推广单元地域
     *
     * @param creativeUnitTable
     * @param type
     */
    public static void handlerLevel3 (AdCreativeUnitTable creativeUnitTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("CreativeUnitIndex is not support");
            return;
        }
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getAdId());
        if (null == unitObject || null == creativeObject) {
            log.error("AdCreativeUnitTable index error: {}", JSON.toJSONString(creativeUnitTable));
        }
        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(
                creativeUnitTable.getAdId(),
                creativeUnitTable.getUnitId()
        );
        handlerBinlogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(
                        creativeUnitObject.getAdId().toString(),
                        creativeUnitObject.getUnitId().toString()
                ),
                creativeUnitObject,
                type
        );
    }

    /**
     * 地域纬度
     *
     * @param unitDistrictTable
     * @param type
     */
    public static void handlerLevel4 (AdUnitDistrictTable unitDistrictTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("district index can not support update");
            return;
        }
        AdUnitObject unitObject = DataTable.of(
                AdUnitIndex.class
        ).get(unitDistrictTable.getUnitId());
        if (null == unitObject) {
            log.error("AdUnitDistrictTable index error:{}", unitDistrictTable.getUnitId());
            return;
        }
        String key = CommonUtils.stringConcat(
                unitDistrictTable.getProvince(), unitDistrictTable.getCity()
        );
        Set<Long> value = new HashSet<>(
                Collections.singleton(unitDistrictTable.getUnitId())
        );
        handlerBinlogEvent(
                DataTable.of(UnitDistrictIndex.class),
                key, value,
                type
        );
    }

    /**
     * 兴趣纬度
     *
     * @param unitItTable
     * @param type
     */
    public static void handlerLevel4 (AdUnitItTable unitItTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("it index can not support update");
            return;
        }
        AdUnitObject unitObject = DataTable.of(
                AdUnitIndex.class
        ).get(unitItTable.getUnitId());
        if (null == unitObject) {
            log.error("AdUnitItTable index error:{}", unitItTable.getUnitId());
            return;
        }
        Set<Long> value = new HashSet<>(
                Collections.singleton(unitItTable.getUnitId())
        );
        handlerBinlogEvent(
                DataTable.of(UnitItIndex.class),
                unitItTable.getItTag(),
                value,
                type
        );
    }

    /**
     * 关键词
     *
     * @param unitKeywordTable
     * @param type
     */
    public static void handlerLevel4 (AdUnitKeywordTable unitKeywordTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("keyword index can not support update");
            return;
        }
        AdUnitObject unitObject = DataTable.of(
                AdUnitIndex.class
        ).get(unitKeywordTable.getUnitId());
        if (null == unitObject) {
            log.error("AdUnitKeywordTable index error:{}", unitKeywordTable.getUnitId());
            return;
        }
        Set<Long> value = new HashSet<>(
                Collections.singleton(unitKeywordTable.getUnitId())
        );
        handlerBinlogEvent(
                DataTable.of(UnitKeyWordIndex.class),
                unitKeywordTable.getKeyword(),
                value,
                type
        );
    }

    private static <K, V> void handlerBinlogEvent (IndexAware<K, V> index, K key, V value, OpType type) {

        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }
}
