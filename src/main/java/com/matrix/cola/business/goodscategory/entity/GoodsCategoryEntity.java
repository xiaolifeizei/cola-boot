package com.matrix.cola.business.goodscategory.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.matrix.cola.common.entity.BaseColaEntity;
import lombok.Data;

/**
 * 物资分类实体类
 *
 * @author : cui_feng
 * @since : 2022-06-24 09:55
 */
@Data
@TableName("goods_category")
public class GoodsCategoryEntity extends BaseColaEntity {

    /**
     * 父节点
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类编码
     */
    private String code;
}
