package com.hong.ad.mysql;

import com.alibaba.fastjson.JSON;
import com.hong.ad.constant.OpType;
import com.hong.ad.dto.ParseTemplate;
import com.hong.ad.dto.TableTemplate;
import com.hong.ad.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  11:25 下午 2020/4/24
 */
@Slf4j
@Component
public class TemplateHolder {

    private ParseTemplate template;

    private final JdbcTemplate jdbcTemplate;

    private String SQL_SCHEMA = "select table_schema,table_name,column_name,ordinal_position" +
            " from information_schema.columns" +
            " where table_schema = ?" +
            " and table_name = ?";

    @Autowired
    public TemplateHolder (JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    public TableTemplate getTable (String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    @PostConstruct
    private void init () {
        loadJson("template.json");
    }

    private void loadJson (String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inStream = classLoader.getResourceAsStream(path);
        try {
            Template template = JSON.parseObject(
                    inStream,
                    Charset.defaultCharset(),
                    Template.class
            );
            this.template = ParseTemplate.parse(template);
            loadMeta();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("fail to parse json file");
        }
    }

    private void loadMeta () {
        for (Map.Entry<String, TableTemplate> entry : template.getTableTemplateMap().entrySet()) {
            TableTemplate table = entry.getValue();
            List<String> updateFields = table.getOpTypeFiledSetMap().get(
                    OpType.UPDATE
            );
            List<String> insertFields = table.getOpTypeFiledSetMap().get(
                    OpType.ADD
            );
            List<String> deleteFields = table.getOpTypeFiledSetMap().get(
                    OpType.DELETE
            );
            // jdbcTemplate.query(SQL_SCHEMA, new Object[]{
            //         template.getDatabase(), table.getTableName()
            // }, (rs, i) -> {
            //     int pos = rs.getInt("ORDINAL_POSITION");
            //     String colName = rs.getString("COLUMN_NAME");
            //     if ((null != updateFileds && updateFileds.contains(colName))
            //             || (null != insertFileds && insertFileds.contains(colName))
            //             || (null != deleteFileds && deleteFileds.contains(colName))) {
            //         table.getPosMap().put(pos - 1, colName);
            //     }
            //     return null;
            // });

            jdbcTemplate.query(SQL_SCHEMA, new Object[]{
                    template.getDatabase(), table.getTableName()
            }, (rs, i) -> {
                int pos = rs.getInt("ORDINAL_POSITION");
                String colName = rs.getString("COLUMN_NAME");

                if ((null != updateFields && updateFields.contains(colName))
                        || (null != insertFields && insertFields.contains(colName))
                        || (null != deleteFields && deleteFields.contains(colName))) {
                    table.getPosMap().put(pos - 1, colName);
                }
                return null;
            });
        }
    }
}
