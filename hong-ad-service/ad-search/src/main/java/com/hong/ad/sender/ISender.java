package com.hong.ad.sender;

import com.hong.ad.dto.MySqlRowData;

/**
 * 投递增量数据的实现接口
 *
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  7:20 下午 2020/5/24
 */
public interface ISender {

    /**
     * 接受增量数据
     *
     * @param rowData
     */
    void sender (MySqlRowData rowData);
}
