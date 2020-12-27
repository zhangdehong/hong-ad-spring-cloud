package com.hong.ad.dao;

import com.hong.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  9:30 PM 2019/11/19
 */
public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    AdPlan findByIdAndUserId(Long id,Long userId);

    List<AdPlan> findAllByIdAndUserId (List<Long> ids, Long userId);

    AdPlan findByUserIdAndPlanName(Long userId,String planName);

    List<AdPlan> findAllByPlanStatus(Integer planStatus);
}
