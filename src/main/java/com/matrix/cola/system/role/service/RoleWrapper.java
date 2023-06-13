package com.matrix.cola.system.role.service;

import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.common.service.BaseColaEntityWrapperService;
import com.matrix.cola.system.role.entity.RoleEntity;
import com.matrix.cola.system.role.entity.RoleEntityWrapper;

/**
 * 角色包装类接口
 *
 * @author : cui_feng
 * @since : 2022-05-31 15:56
 */
public interface RoleWrapper extends BaseColaEntityWrapperService<RoleEntity, RoleEntityWrapper> {

    Result getRoleTree(Query<RoleEntity> query);
}
