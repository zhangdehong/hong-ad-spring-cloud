package com.hong.ad.controller;

import com.alibaba.fastjson.JSON;
import com.hong.ad.exception.AdException;
import com.hong.ad.service.IUserService;
import com.hong.ad.vo.CreateUserResponse;
import com.hong.ad.vo.CreateUserResquest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  3:36 PM 2019/11/25
 */
@Slf4j
@RestController
public class UserOpController {

    private final IUserService userService;

    @Autowired
    public UserOpController (IUserService userService) {this.userService = userService;}

    @PutMapping("/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserResquest resquest)throws AdException {
        log.info("ad-sponsor:createUser -> {}", JSON.toJSONString(resquest));
        return userService.createUser(resquest);
    }
}
