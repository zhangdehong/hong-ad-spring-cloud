package com.hong.ad.service.impl;

import com.hong.ad.constant.CommonStatus;
import com.hong.ad.constant.Constant;
import com.hong.ad.dao.AdPlanRepository;
import com.hong.ad.dao.AdUserRepository;
import com.hong.ad.entity.AdPlan;
import com.hong.ad.entity.AdUser;
import com.hong.ad.exception.AdException;
import com.hong.ad.service.IAdPlanService;
import com.hong.ad.utils.CommonUtils;
import com.hong.ad.vo.AdPlanGetRequest;
import com.hong.ad.vo.AdPlanRequest;
import com.hong.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  1:07 PM 2019/11/24
 */
@Slf4j
@Service
public class AdPlanServiceImpl implements IAdPlanService {

    private final AdUserRepository adUserRepository;
    private final AdPlanRepository adPlanRepository;

    @Autowired
    public AdPlanServiceImpl (AdUserRepository adUserRepository, AdPlanRepository adPlanRepository) {
        this.adUserRepository = adUserRepository;
        this.adPlanRepository = adPlanRepository;
    }

    @Override
    @Transactional
    public AdPlanResponse createAdPlan (AdPlanRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constant.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        // 确保关联的User对象是存在的
        Optional<AdUser> addUser = adUserRepository.findById(request.getUserId());
        if (!addUser.isPresent()) {
            throw new AdException(Constant.ErrorMsg.CAN_NOT_FIND_RECORE);
        }
        AdPlan oldPlan = adPlanRepository.findByUserIdAndPlanName(request.getUserId(),
                request.getPlanNname()
        );
        if (oldPlan != null) {
            throw new AdException(Constant.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }
        AdPlan newAdPlan = adPlanRepository.save(new AdPlan(request.getUserId(), request.getPlanNname(),
                        CommonUtils.parseStringDate(request.getStartDate()),
                        CommonUtils.parseStringDate(request.getEndDate())
                )
        );
        return new AdPlanResponse(newAdPlan.getId(), newAdPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds (AdPlanGetRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constant.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        return adPlanRepository.findAllByIdAndUserId(request.getIds(), request.getUserId());
    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan (AdPlanRequest request) throws AdException {
        if (!request.updateValidate()) {
            throw new AdException(Constant.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = adPlanRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (null == plan) {
            throw new AdException(Constant.ErrorMsg.CAN_NOT_FIND_RECORE);
        }
        if (null != request.getPlanNname()) {
            plan.setPlanName(request.getPlanNname());
        }
        if (null != request.getStartDate()) {
            plan.setStartDate(
                    CommonUtils.parseStringDate(request.getStartDate())
            );
        }
        if (null != request.getEndDate()) {
            plan.setEndDate(
                    CommonUtils.parseStringDate(request.getEndDate())
            );
        }
        plan.setUpdateTime(new Date());
        adPlanRepository.save(plan);
        return new AdPlanResponse(plan.getId(), plan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan (AdPlanRequest request) throws AdException {
        if (!request.deleteValidate()) {
            throw new AdException(Constant.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = adPlanRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (null == plan) {
            throw new AdException(Constant.ErrorMsg.CAN_NOT_FIND_RECORE);
        }
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        adPlanRepository.save(plan);
    }
}
