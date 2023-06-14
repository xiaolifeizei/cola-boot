package com.matrix.cola.business.goodspec.entity;

import com.matrix.cola.common.entity.BaseColaEntityWrapper;
import lombok.Data;

/**
 * 规格实体类
 *
 * @author : cui_feng
 * @since : 2022-04-13 13:09
 */
@Data
public class SpecEntityWrapper extends BaseColaEntityWrapper {

    /**
     * 规格名
     */
    private String name;

    /**
     * 关联的物资id
     */
    private Long goodsId;

    /**
     * 关联的物资名称
     */
    private String goodsName;

    /**
     * 备注
     */
    private String remark;
}
