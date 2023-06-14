package com.matrix.cola.business.goods.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.matrix.cola.common.entity.BaseColaEntity;
import lombok.Data;

/**
 * 物资实体类
 *
 * @author : cui_feng
 * @since : 2022-04-13 13:06
 */
@Data
@TableName("goods")
public class GoodsEntity extends BaseColaEntity {

    /**
     * 物资名
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 拼音码
     */
    private String spellCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 虚拟字段，物资分类，添加和查询时接收物资分类信息
     */
    @TableField(exist = false)
    private String categoryId;

    /**
     * 虚拟字段，用于显示物资分类名称
     */
    @TableField(exist = false)
    private String categoryName;
}
