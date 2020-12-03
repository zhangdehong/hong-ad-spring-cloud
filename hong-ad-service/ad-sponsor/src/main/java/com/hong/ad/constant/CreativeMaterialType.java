package com.hong.ad.constant;

import lombok.Getter;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  9:14 PM 2019/11/19
 */
@Getter
public enum CreativeMaterialType {

    JPG(1, "jpg"),
    //PNG(2,"png"),
    BMP(2, "bmp"),
    MP4(3, "mp4"),
    AVI(4, "avi"),
    TEXT(5, "txt");

    private Integer type;
    private String desc;

    CreativeMaterialType (Integer type, String desc) {

    }
}
