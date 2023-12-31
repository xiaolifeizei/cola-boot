package com.matrix.cola.system.user.entity;

import com.matrix.cola.system.role.entity.RoleUserEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 给用户添加角色
 *
 * @author : cui_feng
 * @since : 2022-06-02 12:30
 */
@Data
public class GrantRole implements Serializable {

    /**
     * 用户id
     */
    Long userId;

    /**
     * 角色用户列表
     */
    List<RoleUserEntity> list = new ArrayList<>();
}
