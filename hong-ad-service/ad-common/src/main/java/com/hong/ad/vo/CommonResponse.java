package com.hong.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  4:23 PM 2019/11/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public CommonResponse (Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
