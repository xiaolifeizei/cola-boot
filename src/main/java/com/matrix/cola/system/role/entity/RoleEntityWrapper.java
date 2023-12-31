package com.matrix.cola.system.role.entity;

import com.matrix.cola.common.entity.BaseColaEntityWrapper;
import lombok.Data;

/**
 * 角色包装类
 *
 * @author : cui_feng
 * @since : 2022-05-31 15:52
 */
@Data
public class RoleEntityWrapper extends BaseColaEntityWrapper {

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
