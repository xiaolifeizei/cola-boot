package com.matrix.cola.business.customer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.matrix.cola.common.entity.BaseColaEntity;
import lombok.Data;

/**
 * 客户管理实体类
 *
 * @author : cui_feng
 * @since : 2022-04-07 11:15
 */
@Data
@TableName("customer")
public class CustomerEntity extends BaseColaEntity {

    /**
     * 客户名
     */
    private String name;

    /**
     * 客户名拼音码
     */
    private String spellCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 虚拟字段，分类，添加和查询时接收分类信息
     */
    @TableField(exist = false)
    private String categoryId;

    /**
     * 虚拟字段，用于显示分类名称
     */
    @TableField(exist = false)
    private String categoryName;

}
