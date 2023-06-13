package com.matrix.cola.system.role.controller;

import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.system.role.entity.GrantMenu;
import com.matrix.cola.system.role.entity.RoleEntity;
import com.matrix.cola.system.role.service.RoleMenuService;
import com.matrix.cola.system.role.service.RoleService;
import com.matrix.cola.system.role.service.RoleWrapper;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理
 *
 * @author : cui_feng
 * @since : 2022-05-30 13:18
 */
@RestController
@RequestMapping("/system/role")
@AllArgsConstructor
public class RoleController {

    RoleService roleService;

    RoleMenuService roleMenuService;

    RoleWrapper roleWrapper;

    @PostMapping("/getRoleTree")
    public Result getRoleTree(@RequestBody Query<RoleEntity> query) {
        return roleWrapper.getRoleTree(query);
    }

    @PostMapping("/getRoleList")
    public Result getRoleList(@RequestBody Query<RoleEntity> query) {
        return Result.list(roleService.getList(query));
    }

    @PostMapping("/deleteRole")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result deleteRole(@RequestBody RoleEntity role) {
        return roleService.deleteRole(role);
    }

    @PostMapping("/updateRole")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result updateRole(@RequestBody RoleEntity role) {
        return roleService.modify(role);
    }

    @PostMapping("addRole")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result addRole(@RequestBody RoleEntity role) {
        return roleService.insert(role);
    }

    @PostMapping("/getRoleMenusByRole")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result getRoleMenusByRole(@RequestBody RoleEntity rolePO) {
        return roleMenuService.getRoleMenusByRoleId(rolePO);
    }

    @PostMapping("/grantMenus")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result grantMenus(@RequestBody GrantMenu grantMenu) {
        return roleMenuService.grantMenus(grantMenu);
    }
}
