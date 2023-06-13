package com.matrix.cola.system.role.service.impl;

import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.common.service.AbstractColaEntityWrapperService;
import com.matrix.cola.system.role.entity.RoleEntity;
import com.matrix.cola.system.role.entity.RoleEntityWrapper;
import com.matrix.cola.system.role.entity.RoleTree;
import com.matrix.cola.system.role.service.RoleService;
import com.matrix.cola.system.role.service.RoleWrapper;
import org.springframework.stereotype.Service;

/**
 * 角色包装类接口实现类
 *
 * @author : cui_feng
 * @since : 2022-05-31 15:55
 */
@Service
public class RoleWrapperServiceImpl extends AbstractColaEntityWrapperService<RoleEntity, RoleEntityWrapper, RoleService> implements RoleWrapper {

    @Override
    public Result getRoleTree(Query<RoleEntity> query) {
        return Result.list(RoleTree.getRoleTree(getWrapperList(query)));
    }
}
