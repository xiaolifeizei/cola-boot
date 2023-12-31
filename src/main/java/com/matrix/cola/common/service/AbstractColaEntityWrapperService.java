package com.matrix.cola.common.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.matrix.cola.common.entity.BaseColaEntity;
import com.matrix.cola.common.entity.BaseColaEntityWrapper;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.system.group.entity.GroupEntity;
import com.matrix.cola.system.user.entity.UserEntity;
import com.matrix.cola.system.group.service.GroupService;
import com.matrix.cola.system.user.service.UserService;

import javax.annotation.Resource;

/**
 * 实体包装服务类父类
 *
 * @author : cui_feng
 * @since : 2022-05-19 12:23
 */
public abstract class AbstractColaEntityWrapperService<T extends BaseColaEntity,W extends BaseColaEntityWrapper,S extends BaseColaEntityService<T>> extends AbstractEntityWrapperService<T,W,S> implements BaseColaEntityWrapperService<T, W> {

    @Resource
    UserService userService;

    @Resource
    GroupService groupService;

    /**
     * 此方法用于填充实体对象中的通用字段，此方法会先调用entityWrapper方法
     * @param entity 实体对象
     * @return 实体包装类
     */
    @Override
    public W baseEntityWrapper(T entity) {
        if (ObjectUtil.isNull(entity)) {
            return null;
        }

        W q = super.baseEntityWrapper(entity);

        if (ObjectUtil.isNotNull(entity.getCreator())) {
            String username = cacheProxy.getObjectFromLoader(ColaCacheName.USER_NAME,entity.getCreator().toString(),() -> {
                UserEntity userPO = userService.getOne(entity.getCreator());
                return ObjectUtil.isNull(userPO) ? null : userPO.getName();
            });
            q.setCreatorName(username);
        }

        if (ObjectUtil.isNotNull(entity.getReviser())) {
            String username = cacheProxy.getObjectFromLoader(ColaCacheName.USER_NAME,entity.getCreator().toString(),() -> {
                UserEntity userPO = userService.getOne(entity.getReviser());
                return ObjectUtil.isNull(userPO) ? null : userPO.getName();
            });
            q.setReviserName(username);
        }

        if (ObjectUtil.isNotEmpty(entity.getGroupId())) {
            String [] arrGroupId = entity.getGroupId().split(",");
            for (String groupId : arrGroupId) {
                if (ObjectUtil.isNull(groupId) || StrUtil.isEmpty(groupId.trim())) {
                    continue;
                }
                GroupEntity groupEntity = cacheProxy.getObjectFromLoader(ColaCacheName.GROUP_ENTITY, groupId,() -> {
                    Query<GroupEntity> query = new Query<>();
                    query.eq("id",groupId);
                    return groupService.getOne(query);
                });
                if (ObjectUtil.isNotNull(groupEntity) && StrUtil.isNotEmpty(groupEntity.getName())) {
                    String groupName = groupEntity.getName();
                    if (StrUtil.isNotEmpty(q.getGroupName())) {
                        groupName = "," + groupName;
                    }
                    q.setGroupName(StrUtil.emptyToDefault(q.getGroupName(),"") + groupName);
                }
            }
        }
        return q;
    }

    @Override
    public W getWrapperOne(Long id) {
        return baseEntityWrapper(getService().getOne(id));
    }

    @Override
    public W getWrapperOne(T entity) {
        return baseEntityWrapper(getService().getOne(entity));
    }
}
