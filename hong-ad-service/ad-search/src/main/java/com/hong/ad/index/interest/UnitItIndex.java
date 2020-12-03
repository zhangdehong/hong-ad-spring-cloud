package com.hong.ad.index.interest;

import com.hong.ad.index.IndexAware;
import com.hong.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  10:35 AM 2020/2/9
 */
@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {

    // <itTag adUnitId set>  通过兴趣标签查询推广单元  反向索引
    private static Map<String, Set<Long>> itUnitMap;
    // <unitId idTag set>  通过推广单元id  查询推广兴趣
    private static Map<Long, Set<String>> unitItmap;

    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItmap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get (String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void add (String key, Set<Long> value) {
        log.info("unitItIndex, before add:{}", unitItmap);
        Set<Long> unitIds = CommonUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);
        for (Long unitId : value) {
            Set<String> its = CommonUtils.getOrCreate(unitId, unitItmap, ConcurrentSkipListSet::new);
            its.add(key);
        }
        log.info("unitItIndex, after add:{}", unitItmap);
    }

    @Override
    public void update (String key, Set<Long> value) {
        log.info("it index can not support update");
    }

    @Override
    public void delete (String key, Set<Long> value) {
        log.info("UnitItIndex, before delete:{}", unitItmap);
        Set<Long> unitIds = CommonUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);
        for (Long unitId : value) {
            Set<String> itTagSet = CommonUtils.getOrCreate(unitId, unitItmap, ConcurrentSkipListSet::new);
            itTagSet.remove(key);
        }
        log.info("UnitItIndex, before delete:{}", unitItmap);
    }

    public boolean match (Long unitId, List<String> itTags) {
        if (unitItmap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitItmap.get(unitId))) {
            Set<String> unitKeywords = unitItmap.get(unitId);
            return CollectionUtils.isSubCollection(itTags, unitKeywords);
        }
        return false;
    }
}
