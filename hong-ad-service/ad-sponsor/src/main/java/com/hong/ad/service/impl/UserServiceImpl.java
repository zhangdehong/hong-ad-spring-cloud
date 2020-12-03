package com.hong.ad.service.impl;

import com.hong.ad.constant.Constant;
import com.hong.ad.dao.AdUserRepository;
import com.hong.ad.entity.AdUser;
import com.hong.ad.exception.AdException;
import com.hong.ad.service.IUserService;
import com.hong.ad.utils.CommonUtils;
import com.hong.ad.vo.CreateUserResquest;
import com.hong.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  9:04 PM 2019/11/23
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private AdUserRepository adUserRepository;

    @Autowired
    public UserServiceImpl (AdUserRepository adUserRepository) {
        this.adUserRepository = adUserRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser (CreateUserResquest request)throws AdException {
        if(!request.validate()){
            throw new AdException(Constant.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdUser oldUser = adUserRepository.findByUsername(request.getUsernmae());
        if(null != oldUser){
            throw new AdException(Constant.ErrorMsg.SAME_NAME_ERROR);
        }
        AdUser newUser = adUserRepository.save(new AdUser(request.getUsernmae(),
                CommonUtils.md5(request.getUsernmae())));

        return new CreateUserResponse(newUser.getId(),newUser.getUsername(),newUser.getToken(),
                newUser.getCreateTime(),newUser.getUpdateTime());
    }
}
