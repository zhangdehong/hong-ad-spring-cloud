package com.hong.ad.vo;

import com.hong.ad.constant.CommonStatus;
import com.hong.ad.entity.Creative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  1:19 AM 2019/11/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeRequest {

    private String name;
    private Integer type;
    private Integer materialType;
    private Integer heigth;
    private Integer width;
    private Long size;
    private Integer duration;
    private Long userId;
    private String url;

    public Creative converToEntity () {
        Creative creative = new Creative();
        creative.setName(this.name);
        creative.setType(this.type);
        creative.setMaterialType(this.materialType);
        creative.setHeigth(this.heigth);
        creative.setWidth(this.width);
        creative.setDuration(this.duration);
        creative.setAuditStatus(CommonStatus.VALID.getStatus());
        creative.setUserId(this.userId);
        creative.setSize(this.size);
        creative.setCreateTime(new Date());
        creative.setUpdateTime(creative.getCreateTime());
        return creative;
    }
}
