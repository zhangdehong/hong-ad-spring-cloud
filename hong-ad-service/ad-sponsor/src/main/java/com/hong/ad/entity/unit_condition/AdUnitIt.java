package com.hong.ad.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: ZhangDeHong
 * @Describe: TODO
 * @Date Create in  7:44 PM 2019/11/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ad_unit_it")
public class AdUnitIt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Basic
    @Column(name = "it_tag", nullable = false)
    private String itTag;

    public AdUnitIt (Long unitId, String itTag) {
        this.unitId = unitId;
        this.itTag = itTag;
    }
}
