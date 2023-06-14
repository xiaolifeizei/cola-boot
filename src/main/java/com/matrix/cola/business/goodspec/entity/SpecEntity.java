package com.matrix.cola.business.goodspec.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.matrix.cola.common.entity.BaseColaEntity;
import lombok.Data;

/**
 * 规格实体类
 *
 * @author : cui_feng
 * @since : 2022-04-13 13:09
 */
@Data
@TableName("goods_spec")
public class SpecEntity extends BaseColaEntity {

    /**
     * 物资id
     */
    private Long goodsId;
    /**
     * 规格名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}
