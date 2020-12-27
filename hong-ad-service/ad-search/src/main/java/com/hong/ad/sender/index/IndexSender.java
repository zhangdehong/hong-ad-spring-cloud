package com.hong.ad.sender.index;

import com.alibaba.fastjson.JSON;
import com.hong.ad.dump.table.*;
import com.hong.ad.handler.AdLevelDataHandler;
import com.hong.ad.index.DataLevel;
import com.hong.ad.constant.Constant;
import com.hong.ad.dto.MySqlRowData;
import com.hong.ad.sender.ISender;
import com.hong.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  12:51 上午 2020/6/7
 */
@Slf4j
@Component
public class IndexSender implements ISender {

    /**
     * 增量数据构建增量索引的维护
     *
     * @param rowData
     */
    @Override
    public void sender (MySqlRowData rowData) {
        String level = rowData.getLevel();
        if (DataLevel.LEVEL2.getLevel().equals(level)) {
            level2RowData(rowData);
        } else if (DataLevel.LEVEL3.getLevel().equals(level)) {
            level3RowData(rowData);
        } else if (DataLevel.LEVEL4.getLevel().equals(level)) {
            level4RowData(rowData);
        } else {
            log.info("MYsqlRowData error {}", JSON.toJSONString(rowData));
        }
    }

    /**
     * 实现增量数据的投递 第二层级
     *
     * @param rowData
     */
    private void level2RowData (MySqlRowData rowData) {
        if (rowData.getTableName().equals(
                Constant.AD_PLAN_TABLE_INFO.TABLE_NAME
        )) {
            ArrayList<AdPlanTable> planTables = new ArrayList<>();
            for (Map<String, String> filedValueMap : rowData.getFailedValueMap()) {
                AdPlanTable adPlanTable = new AdPlanTable();
                filedValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_ID:
                            adPlanTable.setId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_USER_ID:
                            adPlanTable.setUserId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_PLAN_STATUS:
                            adPlanTable.setPlanStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_START_DATA:
                            adPlanTable.setStartDate(CommonUtils.parseStringDate(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_END_DATA:
                            adPlanTable.setEndDate(CommonUtils.parseStringDate(v));
                            break;
                    }
                });
                planTables.add(adPlanTable);
            }
            planTables.forEach(p -> AdLevelDataHandler.handlerLevel2(p, rowData.getOpType()));
        } else if (rowData.getTableName().equals(Constant.AD_CREATIVE_TABLE_INFO.TABLE_NAME)) {
            ArrayList<AdCreativeTable> creativeTables = new ArrayList<>();
            for (Map<String, String> fileValueMap : rowData.getFailedValueMap()) {
                AdCreativeTable creativeTable = new AdCreativeTable();
                fileValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_ID:
                            creativeTable.setAdId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.TYEP:
                            creativeTable.setType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE:
                            creativeTable.setMaterialType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                            creativeTable.setHeight(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                            creativeTable.setWidth(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                            creativeTable.setAuditStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_URL:
                            creativeTable.setAdUrl(v);
                            break;
                    }
                });
                creativeTables.add(creativeTable);
            }
            creativeTables.forEach(c -> AdLevelDataHandler.handlerLevel2(c, rowData.getOpType()));
        }
    }

    /**
     * 实现增量数据的投递 第三层级
     *
     * @param rowData
     */
    private void level3RowData (MySqlRowData rowData) {
        if (rowData.getTableName().equals(
                Constant.AD_UNIT_TABLE_INFO.TABLE_NAME
        )) {
            List<AdUnitTable> unitTables = new ArrayList<>();
            for (Map<String, String> filedValueMap : rowData.getFailedValueMap()) {
                AdUnitTable unitTable = new AdUnitTable();
                filedValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_ID:
                            unitTable.setUnitId(Long.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_UNIT_STATUS:
                            unitTable.setUnitStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_POSITION_TYPE:
                            unitTable.setPositionType(Integer.valueOf(v));
                            break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_PLAN_ID:
                            unitTable.setPlanId(Long.valueOf(v));
                            break;
                    }
                });
                unitTables.add(unitTable);
            }
            unitTables.forEach(u -> AdLevelDataHandler.handlerLevel3(u, rowData.getOpType()));
        } else if (rowData.getTableName().equals(Constant.AD_CREATIVE_UNIT_TABLE_INFO.TABLE_NAME)) {
            List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();
            for (Map<String, String> filedValueMap : rowData.getFailedValueMap()) {
                AdCreativeUnitTable creativeUnitTable = new AdCreativeUnitTable();
                filedValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_CREATIVE_ID:
                            creativeUnitTable.setAdId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_UNIT_ID:
                            creativeUnitTable.setUnitId(Long.valueOf(v));
                            break;
                    }
                });
                creativeUnitTables.add(creativeUnitTable);
            }
            creativeUnitTables.forEach(c -> AdLevelDataHandler.handlerLevel3(c, rowData.getOpType()));
        }
    }

    /**
     * 第四层级增量数据的投递
     *
     * @param rowData
     */
    private void level4RowData (MySqlRowData rowData) {
        switch (rowData.getTableName()) {
            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.TABLE_NAME:
                List<AdUnitDistrictTable> unitDistrictTables = new ArrayList<>();
                for (Map<String, String> filedValueMap : rowData.getFailedValueMap()) {
                    AdUnitDistrictTable unitDistrictTable = new AdUnitDistrictTable();
                    filedValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_UNIT_ID:
                                unitDistrictTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_PROVINCE:
                                unitDistrictTable.setProvince(v);
                                break;
                            case Constant.AD_UNIT_DISTRICT_TABLE_INFO.COLUMN_CITY:
                                unitDistrictTable.setCity(v);
                                break;
                        }
                    });
                    unitDistrictTables.add(unitDistrictTable);
                }
                unitDistrictTables.forEach(d -> AdLevelDataHandler.handlerLevel4(d, rowData.getOpType()));
                break;
            case Constant.AD_UNIT_IT_TABLE_INFO.TABLE_NAME:
                List<AdUnitItTable> unitItTables = new ArrayList<>();
                for (Map<String, String> filedValueMap : rowData.getFailedValueMap()) {
                    AdUnitItTable unitItTable = new AdUnitItTable();
                    filedValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_TABLE_INFO.COLUMN_ID:
                                unitItTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_IT_TABLE_INFO.COLUMN_IT_TAG:
                                unitItTable.setItTag(v);
                                break;
                        }
                    });
                    unitItTables.add(unitItTable);
                }
                unitItTables.forEach(i -> AdLevelDataHandler.handlerLevel4(i, rowData.getOpType()));
                break;
            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.TABLE_NAME:
                List<AdUnitKeywordTable> keywordTables = new ArrayList<>();
                for (Map<String, String> filedValueMap : rowData.getFailedValueMap()) {
                    AdUnitKeywordTable keywordTable = new AdUnitKeywordTable();
                    filedValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_UNIT_ID:
                                keywordTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.AD_UNIT_KEYWORD_TABLE_INFO.COLUMN_KEYWORD:
                                keywordTable.setKeyword(v);
                                break;
                        }
                    });
                    keywordTables.add(keywordTable);
                }
                keywordTables.forEach(k -> AdLevelDataHandler.handlerLevel4(k, rowData.getOpType()));
                break;
        }
    }
}
