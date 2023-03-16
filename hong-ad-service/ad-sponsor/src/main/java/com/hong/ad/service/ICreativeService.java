package com.hong.ad.service;

import com.hong.ad.exception.AdException;
import com.hong.ad.vo.CreativeRequest;
import com.hong.ad.vo.CreativeResponse;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  1:18 AM 2019/11/25
 */
public interface ICreativeService {

    /**
     * 创建创意
     *
     * @param request
     * @return
     * @throws AdException
     */
    CreativeResponse createCreative (CreativeRequest request) throws AdException;
}
