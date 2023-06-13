package com.matrix.cola.system.role.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.matrix.cola.common.entity.BaseColaEntity;
import lombok.Data;

/**
 * 角色实体类
 *
 * @author : cui_feng
 * @since : 2022-05-11 10:40
 */
@Data
@TableName("system_role")
public class RoleEntity extends BaseColaEntity {

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 父角色
     */
    private Long parentId;
}
