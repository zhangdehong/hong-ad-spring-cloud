package com.hong.ad.mysql.listener;

import com.hong.ad.dto.BinlogRowData;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  12:34 上午 2020/5/13
 */
public interface IListener {

    /**
     * 注册不同的监听器用
     */
    void register ();

    /**
     * 实现增量数据索引的更新
     *
     * @param eventData
     */
    void onEvent (BinlogRowData eventData);
}
