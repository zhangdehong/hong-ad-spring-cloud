package com.hong.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.hong.ad.constant.Constant;
import com.hong.ad.constant.OpType;
import com.hong.ad.dto.BinlogRowData;
import com.hong.ad.dto.MySqlRowData;
import com.hong.ad.dto.TableTemplate;
import com.hong.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  7:23 下午 2020/5/24
 */
@Slf4j
@Component
public class IncrementListener implements IListener {

    /**
     * 投递接口，根据name选择注入
     */
    @Autowired
    private ISender sender;

    private final AggregationListener aggregationListener;

    @Autowired
    public IncrementListener (AggregationListener aggregationListener) {this.aggregationListener = aggregationListener;}

    /**
     * 实现将表去注册
     */
    @Override
    @PostConstruct
    public void register () {
        log.info("incrementListener register db and table info");
        // v  数据库名字   k 表名字
        Constant.table2Db.forEach((k, v) -> aggregationListener.register(
                v, k, this
        ));
    }

    /**
     * @param eventData
     */
    @Override
    public void onEvent (BinlogRowData eventData) {
        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();
        // 包装成最后需要投递的数据
        MySqlRowData rowData = new MySqlRowData();
        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);
        // 取出模板中该操作对应的字段列表
        List<String> feildList = table.getOpTypeFiledSetMap().get(opType);
        if (null == feildList) {
            log.warn("{} , {} not support for", opType, table.getTableName());
            return;
        }
        for (Map<String, String> afterMap : eventData.getAfter()) {
            HashMap<String, String> _afterMap = new HashMap<>(16);
            for (Map.Entry<String, String> entry : afterMap.entrySet()) {
                String colName = entry.getKey();
                String colValue = entry.getValue();
                _afterMap.put(colName, colValue);
            }
            rowData.getFailedValueMap().add(_afterMap);
        }
        sender.sender(rowData);
    }
}
