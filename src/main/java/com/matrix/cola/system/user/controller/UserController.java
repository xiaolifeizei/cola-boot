package com.matrix.cola.system.user.controller;

import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.system.user.entity.GrantRole;
import com.matrix.cola.system.user.entity.UserEntity;
import com.matrix.cola.system.user.entity.UserEntityWrapper;
import com.matrix.cola.system.role.service.RoleUserService;
import com.matrix.cola.system.user.service.UserService;
import com.matrix.cola.system.user.service.UserWrapper;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 *
 * @author : cui_feng
 * @since : 2022-05-05 15:04
 */
@RestController
@RequestMapping("/system/user")
@AllArgsConstructor
public class UserController {

    UserService userService;

    RoleUserService roleUserService;

    UserWrapper userWrapper;

    @PostMapping("/getUserPage")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result getUserPage(@RequestBody Query<UserEntity> query) {
        return userWrapper.getCurrentUsersPage(query);
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result addUser(@RequestBody UserEntity userPO) {
        return userService.insert(userPO);
    }

    @PostMapping("/updateUser")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result updateUser(@RequestBody UserEntity userPO) {
        return userService.modify(userPO);
    }

    @PostMapping("/deleteUser")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result deleteUser(@RequestBody UserEntity userPO) {
        return userService.remove(userPO);
    }

    @PostMapping("/getRoleUsersByUser")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result getRoleUsersByUser(@RequestBody UserEntity userPO) {
        return roleUserService.getRoleUsersByUser(userPO);
    }

    @PostMapping("/grantRoles")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result grantRoles(@RequestBody GrantRole grantRole) {
        return userService.grantRoles(grantRole);
    }
    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody UserEntityWrapper user) {
        return userService.updateUserInfo(user);
    }

    @PostMapping("/resetPassword")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result resetPassword(@RequestBody UserEntity userPO) {
        return userService.resetPassword(userPO);
    }
}
