package com.hong.ad.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  10:17 AM 2020/2/25
 */
public enum OpType {

    ADD,
    UPDATE,
    DELETE,
    OTHER;

    /**
     * EventType -》 OpType
     *
     * @param eventType
     * @return
     */
    public static OpType to (EventType eventType) {
        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
