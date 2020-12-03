package com.hong.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  11:06 下午 2020/6/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class App {

    // 应用编码
    private String appCode;
    // 应用名称
    private String appName;
    // 应用名称
    private String packageName;
    // activity 名称
    private String activityName;

}
