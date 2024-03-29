package com.hong.ad.index.adunit;

import com.hong.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  5:15 PM 2020/1/30
 */
@Slf4j
@Component
public class AdUnitIndex implements IndexAware<Long, AdUnitObject> {

    private static Map<Long, AdUnitObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    public Set<Long> match (Integer positionType) {
        Set<Long> adUnitIds = new HashSet<>();
        objectMap.forEach((k, v) -> {
            if (AdUnitObject.isAdSlotOk(positionType, v.getPositionType())) {
                adUnitIds.add(k);
            }
        });
        return adUnitIds;
    }

    public List<AdUnitObject> fetch (Collection<Long> adUnitIds) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return Collections.emptyList();
        }
        List<AdUnitObject> result = new ArrayList<>();
        adUnitIds.forEach(u ->{
            AdUnitObject object = get(u);
            if(null == object){
                log.info("AdUnitObject is not found {}",u);
                return;
            }
            result.add(object);
        });
        return result;
    }

    @Override
    public AdUnitObject get (Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add (Long key, AdUnitObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add:{}", objectMap);
    }

    @Override
    public void update (Long key, AdUnitObject value) {
        log.info("before update:{}", objectMap);
        AdUnitObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
        log.info("after update:{}", objectMap);
    }

    @Override
    public void delete (Long key, AdUnitObject value) {
        log.info("before delete:{}", objectMap);
        objectMap.remove(key);
        log.info("after delete:{}", objectMap);
    }
}
