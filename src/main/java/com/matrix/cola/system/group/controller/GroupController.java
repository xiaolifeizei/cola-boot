package com.matrix.cola.system.group.controller;

import com.matrix.cola.common.Result;
import com.matrix.cola.common.entity.Query;
import com.matrix.cola.system.group.entity.GroupEntity;
import com.matrix.cola.system.user.entity.UserEntity;
import com.matrix.cola.system.group.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组织机构管理
 *
 * @author : cui_feng
 * @since : 2022-05-30 17:25
 */
@RestController
@RequestMapping("/system/group")
@AllArgsConstructor
public class GroupController {

    GroupService groupService;

    @PostMapping("/getGroupTreeByUser")
    public Result getGroupTreeByUser(UserEntity userPO) {
        return groupService.getGroupTreeByUser(userPO);
    }

    @PostMapping("/getGroupTreeByCurrentUser")
    public Result getGroupTreeByCurrentUser() {
        return groupService.getGroupTreeByCurrentUser();
    }

    @PostMapping("/getGroupTree")
    public Result getGroupTree(@RequestBody Query<GroupEntity> query) {
        return groupService.getGroupTree(query);
    }

    @PostMapping("/addGroup")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result addGroup(@RequestBody GroupEntity groupPO) {
        return groupService.insert(groupPO);
    }

    @PostMapping("/updateGroup")
    @PreAuthorize("hasAuthority('administrator')" + "|| hasAuthority('admin')")
    public Result updateGroup(@RequestBody GroupEntity groupPO) {
        return groupService.modify(groupPO);
    }
}
