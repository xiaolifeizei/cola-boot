package com.matrix.cola.business.custcategory.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.matrix.cola.common.entity.BaseColaEntity;
import lombok.Data;

/**
 * 客户分类实体类
 *
 * @author : cui_feng
 * @since : 2022-06-28 10:18
 */
@Data
@TableName("customer_category")
public class CustomerCategoryEntity extends BaseColaEntity {

    /**
     * 父节点
     */
    private Long parentId;

    /**
     * 客户分类名称
     */
    private String name;

    /**
     * 客户分类编码
     */
    private String code;
}
