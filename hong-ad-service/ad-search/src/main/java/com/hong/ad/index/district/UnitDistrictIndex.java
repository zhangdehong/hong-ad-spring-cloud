package com.hong.ad.index.district;

import com.hong.ad.index.IndexAware;
import com.hong.ad.search.vo.feature.DistrictFeature;
import com.hong.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  11:35 AM 2020/2/10
 */
@Slf4j
@Component
public class UnitDistrictIndex implements IndexAware<String, Set<Long>> {

    private static Map<String, Set<Long>> districtUnitMap;
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    // 省-市  组合key
    @Override
    public Set<Long> get (String key) {
        return districtUnitMap.get(key);
    }

    @Override
    public void add (String key, Set<Long> value) {
        log.info("unitDistrictIndex, before add:{}", unitDistrictMap);
        Set<Long> unitIds = CommonUtils.getOrCreate(key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);
        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getOrCreate(unitId, unitDistrictMap, ConcurrentSkipListSet::new);
            districts.add(key);
        }
        log.info("unitDistrictIndex, after add:{}", unitDistrictMap);
    }

    @Override
    public void update (String key, Set<Long> value) {
        log.error("district index can not support update");
    }

    @Override
    public void delete (String key, Set<Long> value) {
        log.info("unitDistrictIndex, before delete:{}", unitDistrictMap);
        Set<Long> unitIds = CommonUtils.getOrCreate(key, districtUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);
        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getOrCreate(unitId, unitDistrictMap, ConcurrentSkipListSet::new);
            districts.remove(key);
        }
        log.info("unitDistrictIndex, after delete:{}", unitDistrictMap);
    }

    public boolean match (Long adUnitId, List<DistrictFeature.ProvinceAndCity> districts) {
        if (unitDistrictMap.containsKey(adUnitId) && CollectionUtils.isNotEmpty(unitDistrictMap.get(adUnitId))) {
            Set<String> unitDistricts = unitDistrictMap.get(adUnitId);
            List<String> targetDistricts = districts.stream().map(
                    d -> CommonUtils.stringConcat(d.getProvince(), d.getCity())
            ).collect(Collectors.toList());
            // 是否子集 targetDistricts 是否 unitDistricts 子集 如果是返回true  否则返回false
            return CollectionUtils.isSubCollection(targetDistricts, unitDistricts);
        }
        return false;
    }
}
