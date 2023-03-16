package com.hong.ad.dao;

import com.hong.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  9:23 PM 2019/11/19
 */
public interface AdUserRepository extends JpaRepository<AdUser,Long> {

    /**
     * <h2>根据用户名查找数据记录</h2>
     * @param username
     * @return
     */
    AdUser findByUsername(String username);
}
