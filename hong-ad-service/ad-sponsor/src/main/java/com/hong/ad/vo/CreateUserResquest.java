package com.hong.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  8:57 PM 2019/11/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResquest {

    private String usernmae;

    public boolean validate () {
        return !StringUtils.isEmpty(this.usernmae);
    }
}
