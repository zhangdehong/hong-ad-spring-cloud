package com.hong.ad.service;

import com.hong.ad.exception.AdException;
import com.hong.ad.vo.*;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  4:09 PM 2019/11/24
 */
public interface IAdUnitService {

    /**
     * 创建推广单元
     *
     * @param resquest
     * @return
     * @throws AdException
     */
    AdUnitResponse createAdUnit (AdUnitRequest resquest) throws AdException;

    /**
     * 创建推广单元关键词
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitKewordResponse createUnitKeword (AdUnitKewordRequest request) throws AdException;

    /**
     * 创建推广单元兴趣
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitItResponse createUnitIt (AdUnitItRequest request) throws AdException;

    /**
     * 创建推广单元地域
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitDistrictResponse createUnitDistrict (AdUnitDistrictRequest request) throws AdException;

    CreativeUnitResponse createCreativeUnit (CreativeUnitRequest request) throws AdException;
}
