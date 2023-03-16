package com.hong.ad.service.impl;

import com.hong.ad.dao.CreativeRepository;
import com.hong.ad.entity.Creative;
import com.hong.ad.exception.AdException;
import com.hong.ad.service.ICreativeService;
import com.hong.ad.vo.CreativeRequest;
import com.hong.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  1:32 AM 2019/11/25
 */
@Slf4j
@Service
public class CreativeServiceImpl implements ICreativeService {


    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl (CreativeRepository creativeRepository) {this.creativeRepository = creativeRepository;}

    @Override
    public CreativeResponse createCreative (CreativeRequest request) throws AdException {
        Creative creative = creativeRepository.save(
                request.converToEntity()
        );
        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
