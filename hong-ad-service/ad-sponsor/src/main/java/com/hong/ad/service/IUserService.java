package com.hong.ad.service;

import com.hong.ad.exception.AdException;
import com.hong.ad.vo.CreateUserResquest;
import com.hong.ad.vo.CreateUserResponse;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  8:56 PM 2019/11/23
 */
public interface IUserService  {

    CreateUserResponse createUser(CreateUserResquest request)throws AdException;
}
