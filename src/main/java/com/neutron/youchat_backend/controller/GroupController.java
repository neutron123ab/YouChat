package com.neutron.youchat_backend.controller;

import com.neutron.youchat_backend.common.Result;
import com.neutron.youchat_backend.entity.Group;
import com.neutron.youchat_backend.entity.UserGroupRelation;
import com.neutron.youchat_backend.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    /**
     * 创建群聊以及创建用户与群聊的对应关系
     *
     * @param group 群组信息（群主id，groupName，container容量）
     * @return 是否创建成功
     */
    @PostMapping("createGroup")
    public Result<String> createGroup(@RequestBody Group group) {
        if (groupService.createGroup(group) > 0) {
            //在创建好群聊后，会自动为group对象设置一个id
            groupService.addUserGroup(group);
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 向群聊中添加用户
     *
     * @param userGroupRelation 用户-群聊关联信息
     * @return 是否添加成功
     */
    @PostMapping("/addMember")
    public Result<String> addMember(@RequestBody UserGroupRelation userGroupRelation) {
        if (groupService.addUserInGroup(userGroupRelation.getGroupId(), userGroupRelation.getUserId()) > 0) {
            //修改群成员数量
            groupService.updateGroupNum(userGroupRelation.getGroupId(), 1);
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 删除群成员（可以批量删除）
     *
     * @param userGroupList 包含用户-群组关联信息的list集合
     * @return 是否删除成功
     */
    @DeleteMapping("/deleteMember")
    public Result<String> deleteMember(@RequestBody List<UserGroupRelation> userGroupList) {
        if (groupService.deleteUserInGroup(userGroupList) > 0) {
            int size = userGroupList.size();
            Integer groupId = userGroupList.get(0).getGroupId();
            groupService.updateGroupNum(groupId, -size);

            return Result.success();
        }

        return Result.error();
    }

    /**
     * 根据用户id查询用户已加入的所有群组
     *
     * @param userId 用户id
     * @return 用户加入的群组
     */
    @GetMapping
    public Result<List<Group>> getAllGroupsUserJoined(Integer userId) {
        List<Group> groupList = groupService.queryAllGroupsUserJoined(userId);
        if (groupList != null) {
            return Result.success(groupList);
        }
        return Result.error();
    }

}
