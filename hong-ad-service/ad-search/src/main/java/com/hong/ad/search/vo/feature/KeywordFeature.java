package com.hong.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 关键词
 *
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  11:17 下午 2020/6/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeywordFeature {

    // 关键词
    private List<String> keywords;

}
