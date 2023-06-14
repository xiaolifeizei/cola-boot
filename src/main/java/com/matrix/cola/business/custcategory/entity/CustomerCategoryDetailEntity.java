package com.matrix.cola.business.custcategory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.matrix.cola.common.entity.BaseEntity;
import lombok.Data;

/**
 * 客户分类关联实体类
 *
 * @author : cui_feng
 * @since : 2022-06-28 10:25
 */
@Data
@TableName("customer_category_detail")
public class CustomerCategoryDetailEntity extends BaseEntity {

    /**
     * id号
     * 默认数据库自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 关联的客户
     */
    private Long customerId;

    /**
     * 客户分类
     */
    private Long categoryId;
}
