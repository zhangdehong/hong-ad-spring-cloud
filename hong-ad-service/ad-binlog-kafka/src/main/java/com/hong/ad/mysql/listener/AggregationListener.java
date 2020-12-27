package com.hong.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.hong.ad.mysql.TemplateHolder;
import com.hong.ad.dto.BinlogRowData;
import com.hong.ad.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 实现binlog事件的监听
 *
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  12:41 上午 2020/5/13
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tableName;
    private Map<String, IListener> listenerMap = new HashMap<>();

    private final TemplateHolder templateHolder;

    @Autowired
    public AggregationListener (TemplateHolder templateHolder) {this.templateHolder = templateHolder;}

    private String genkey (String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    /**
     * 实现一个注册方法
     *
     * @param _dbName
     * @param _tableName
     * @param listener
     */
    public void register (String _dbName, String _tableName, IListener listener) {
        log.info("register : {}-{}", _dbName, _tableName);
        this.listenerMap.put(genkey(_dbName, _tableName), listener);
    }

    @Override
    public void onEvent (Event event) {
        EventType eventType = event.getHeader().getEventType();
        log.debug("event type :{}", eventType);
        if (eventType == EventType.TABLE_MAP) {
            TableMapEventData eventData = event.getData();
            this.tableName = eventData.getTable();
            this.dbName = eventData.getDatabase();
            return;
        }
        if (eventType != EventType.EXT_UPDATE_ROWS
                && eventType != EventType.EXT_WRITE_ROWS
                && eventType != EventType.EXT_DELETE_ROWS) {
            return;
        }
        // 表名和库名是否已经完成填充
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("no mate data event");
            return;
        }
        // 找出当前表有兴趣的监听器
        String genkey = genkey(this.dbName, this.tableName);
        IListener iListener = this.listenerMap.get(genkey);
        if (null == iListener) {
            log.debug("skip {}", genkey);
            return;
        }
        log.info("tigger event:{}", eventType.name());
        try {
            BinlogRowData rowData = buildRowData(event.getData());
            if (null == rowData) {
                return;
            }
            rowData.setEventType(eventType);
            // 实现增量数据的更新
            iListener.onEvent(rowData);
        } catch (Exception e) {
            e.getStackTrace();
            log.error(e.getMessage());
        } finally {
            this.dbName = "";
            this.tableName = "";
        }
    }

    private List<Serializable[]> getAfterValues (EventData eventData) {
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }
        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream()
                    .map(Map.Entry::getValue).collect(Collectors.toList());
        }
        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }
        return Collections.emptyList();
    }

    /**
     * binlong 数据解析为binLogRowData
     *
     * @param eventData
     * @return
     */
    private BinlogRowData buildRowData (EventData eventData) {
        TableTemplate table = templateHolder.getTable(tableName);
        if (null == table) {
            log.warn("table {} not found", tableName);
            return null;
        }
        List<Map<String, String>> afterMapList = new ArrayList<>();
        for (Serializable[] after : getAfterValues(eventData)) {
            Map<String, String> afterMap = new HashMap<>();
            int collect = after.length;
            for (int ix = 0; ix < collect; ++ix) {
                // 取出当前位置对应的列名
                String colName = table.getPosMap().get(ix);
                // 如果没有 则说明不关心这个列
                if (null == colName) {
                    log.debug("ignore position : {}", ix);
                    continue;
                }
                String colValue = after[ix].toString();
                afterMap.put(colName, colValue);
            }
            afterMapList.add(afterMap);
        }
        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);
        return rowData;
    }
}
