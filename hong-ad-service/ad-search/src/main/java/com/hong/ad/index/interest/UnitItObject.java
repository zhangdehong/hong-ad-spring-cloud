package com.hong.ad.index.interest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  10:31 AM 2020/2/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitItObject {

    // 关联id
    private Long unitId;
    // 兴趣标签
    private String itTag;
}
