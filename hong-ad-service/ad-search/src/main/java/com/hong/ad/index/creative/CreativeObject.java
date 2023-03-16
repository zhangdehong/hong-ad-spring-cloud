package com.hong.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  3:47 PM 2020/2/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeObject {

    // 广告id
    private Long adId;
    // 创意名称
    private String name;
    // 创意类型
    private Integer type;
    // 子类型
    private Integer materialType;
    // 高度
    private Integer height;
    // 宽度
    private Integer width;
    // 审核状态
    private Integer auditStatus;
    // 广告创意URL
    private String adUrl;

    public void update (CreativeObject newObject) {
        if (null != newObject.getAdId()) {
            this.adId = newObject.getAdId();
        }
        if (null != newObject.getName()) {
            this.name = newObject.getName();
        }
        if (null != newObject.getType()) {
            this.type = newObject.getType();
        }
        if (null != newObject.getMaterialType()) {
            this.materialType = newObject.getMaterialType();
        }
        if(null != newObject.getHeight()){
            this.height = newObject.getHeight();
        }
        if(null != newObject.getWidth()){
            this.width = newObject.getWidth();
        }
        if(null != newObject.getAuditStatus()){
            this.auditStatus = newObject.getAuditStatus();
        }
        if(null != newObject.getAdUrl()){
            this.adUrl = newObject.getAdUrl();
        }
    }
}
