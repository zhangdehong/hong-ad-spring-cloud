package com.hong.ad.service.impl;

import com.hong.ad.constant.Constant;
import com.hong.ad.dao.AdPlanRepository;
import com.hong.ad.dao.AdUnitRepository;
import com.hong.ad.dao.CreativeRepository;
import com.hong.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.hong.ad.dao.unit_condition.AdUnitItRepository;
import com.hong.ad.dao.unit_condition.AdUnitkeywordRepository;
import com.hong.ad.dao.unit_condition.CreativeUnitRepository;
import com.hong.ad.entity.AdPlan;
import com.hong.ad.entity.AdUnit;
import com.hong.ad.entity.unit_condition.AdUnitDistrict;
import com.hong.ad.entity.unit_condition.AdUnitIt;
import com.hong.ad.entity.unit_condition.AdUnitKeyword;
import com.hong.ad.entity.unit_condition.CreativeUnit;
import com.hong.ad.exception.AdException;
import com.hong.ad.service.IAdUnitService;
import com.hong.ad.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  4:29 PM 2019/11/24
 */
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private final AdPlanRepository planRepository;
    private final AdUnitRepository unitRepository;

    private final AdUnitkeywordRepository unitkeywordRepository;
    private final AdUnitItRepository unitItRepository;
    private final AdUnitDistrictRepository unitDistrictRepository;

    private final CreativeRepository creativeRepository;
    private final CreativeUnitRepository creativeUnitRepository;

    @Autowired
    public AdUnitServiceImpl (AdPlanRepository planRepository, AdUnitRepository unitRepository,
                              AdUnitDistrictRepository unitDistrictRepository,
                              AdUnitItRepository unitItRepository, AdUnitkeywordRepository unitkeywordRepository,
                              CreativeRepository creativeRepository, CreativeUnitRepository creativeUnitRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.unitDistrictRepository = unitDistrictRepository;
        this.unitItRepository = unitItRepository;
        this.unitkeywordRepository = unitkeywordRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
    }

    @Override
    public AdUnitResponse createAdUnit (AdUnitRequest resquest) throws AdException {
        if (!resquest.createValidate()) {
            throw new AdException(Constant.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<AdPlan> oldPlan = planRepository.findById(resquest.getPlanId());
        if (!oldPlan.isPresent()) {
            throw new AdException(Constant.ErrorMsg.CAN_NOT_FIND_RECORE);
        }
        AdUnit oldAdUnit = unitRepository.findByPlanIdAndUnitName(resquest.getPlanId(),
                resquest.getUnitName());

        if (null != oldAdUnit) {
            throw new AdException(Constant.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }
        AdUnit newAdUnit = unitRepository.save(new AdUnit(resquest.getPlanId(),
                resquest.getUnitName(), resquest.getPositionType(),
                resquest.getBudget())
        );
        return new AdUnitResponse(newAdUnit.getId(), newAdUnit.getUnitName());
    }

    @Override
    public AdUnitKewordResponse createUnitKeword (AdUnitKewordRequest request) throws AdException {
        List<Long> unitIds = request.getUnitKewords().stream()
                .map(AdUnitKewordRequest.UnitKeword::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constant.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<Long> ids = Collections.emptyList();
        List<AdUnitKeyword> unitKeywords = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitKewords())) {
            request.getUnitKewords().forEach(i -> unitKeywords.add(
                    new AdUnitKeyword(i.getUnitId(), i.getKeword())
            ));
            ids = unitkeywordRepository.saveAll(unitKeywords).stream()
                    .map(AdUnitKeyword::getId)
                    .collect(Collectors.toList());
        }
        return new AdUnitKewordResponse(ids);
    }

    @Override
    public AdUnitItResponse createUnitIt (AdUnitItRequest request) throws AdException {
        List<Long> unitIds = request.getUnitIts().stream()
                .map(AdUnitItRequest.UnitIt::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constant.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<AdUnitIt> unitIts = new ArrayList<>();
        request.getUnitIts().forEach(i -> unitIts.add(
                new AdUnitIt(i.getUnitId(), i.getItTag())
        ));
        List<Long> ids = unitItRepository.saveAll(unitIts).stream()
                .map(AdUnitIt::getId).collect(Collectors.toList());
        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createUnitDistrict (AdUnitDistrictRequest request) throws AdException {
        List<Long> unitIds = request.getUnitDistricts().stream()
                .map(AdUnitDistrictRequest.UnitDistrict::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constant.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistricts().forEach(d -> unitDistricts.add(
                new AdUnitDistrict(d.getUnitId(), d.getProvince(), d.getCity())
        ));
        List<Long> ids = unitDistrictRepository.saveAll(unitDistricts).stream().map(AdUnitDistrict::getId)
                .collect(Collectors.toList());
        return new AdUnitDistrictResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit (CreativeUnitRequest request) throws AdException {
        List<Long> creativeIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .collect(Collectors.toList());
        List<Long> unitIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getUnitId)
                .collect(Collectors.toList());
        if (!(isRelatedUnitExist(unitIds) && isRelatedCrativeExist(creativeIds))) {
            throw new AdException(Constant.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<CreativeUnit> creativeUnits = new ArrayList<>();
        request.getUnitItems().forEach(i -> creativeUnits.add(
                new CreativeUnit(i.getCreativeId(), i.getUnitId())
        ));
        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits).stream()
                .map(CreativeUnit::getId)
                .collect(Collectors.toList());

        return new CreativeUnitResponse(ids);
    }

    private boolean isRelatedUnitExist (List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }
        return unitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCrativeExist (List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }
        return creativeRepository.findAllById(creativeIds).size() == new HashSet<>(creativeIds).size();
    }
}
