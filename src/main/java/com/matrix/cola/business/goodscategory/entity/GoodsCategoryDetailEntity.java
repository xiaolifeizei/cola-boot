package com.matrix.cola.business.goodscategory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.matrix.cola.common.entity.BaseEntity;
import lombok.Data;

/**
 * 分类、物资关联
 *
 * @author : cui_feng
 * @since : 2022-06-24 09:59
 */
@Data
@TableName("goods_category_detail")
public class GoodsCategoryDetailEntity extends BaseEntity {

    /**
     * id号
     * 默认数据库自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 分类Id
     */
    private Long categoryId;

    /**
     * 物资
     */
    private Long goodsId;
}
