package com.hong.ad.index;

import com.alibaba.fastjson.JSON;
import com.hong.ad.dump.DConstant;
import com.hong.ad.dump.table.*;
import com.hong.ad.handler.AdLevelDataHandler;
import com.hong.ad.constant.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  10:48 AM 2020/4/12
 */
@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    /**
     * 类加载之后加载数据
     * 第二层级和第三层级的数据文件加载到检索系统中，构成全量索引
     */
    @PostConstruct
    public void init () {
        List<String> adPlanStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN)
        );
        adPlanStrings.forEach(p -> AdLevelDataHandler.handlerLevel2(
                JSON.parseObject(p, AdPlanTable.class),
                OpType.ADD
        ));
        List<String> adCreativeStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE)
        );
        adCreativeStrings.forEach(c -> AdLevelDataHandler.handlerLevel2(
                JSON.parseObject(c, AdCreativeTable.class),
                OpType.ADD
        ));
        List<String> adUnitsStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT)
        );
        adUnitsStrings.forEach(u -> AdLevelDataHandler.handlerLevel3(
                JSON.parseObject(u, AdUnitTable.class),
                OpType.ADD
        ));
        List<String> adCreativeUnitStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT)
        );
        adCreativeUnitStrings.forEach(cu -> AdLevelDataHandler.handlerLevel3(
                JSON.parseObject(cu, AdCreativeUnitTable.class),
                OpType.ADD
        ));
        List<String> adUnitDistrictStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT)
        );
        adUnitDistrictStrings.forEach(ud -> AdLevelDataHandler.handlerLevel4(
                JSON.parseObject(ud, AdUnitDistrictTable.class),
                OpType.ADD
        ));
        List<String> adUnitItStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT)
        );
        adUnitItStrings.forEach(ui -> AdLevelDataHandler.handlerLevel4(
                JSON.parseObject(ui, AdUnitItTable.class),
                OpType.ADD
        ));
        List<String> adUnitKeywordsStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD)
        );
        adUnitKeywordsStrings.forEach(uk -> AdLevelDataHandler.handlerLevel4(
                JSON.parseObject(uk, AdUnitKeywordTable.class),
                OpType.ADD
        ));
    }

    private List<String> loadDumpData (String fileName) {
        try (BufferedReader br = Files.newBufferedReader(
                Paths.get(fileName)
        )) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
