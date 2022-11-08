package com.neutron.youchat_backend.service.impl;

import com.neutron.youchat_backend.entity.Group;
import com.neutron.youchat_backend.entity.UserGroupRelation;
import com.neutron.youchat_backend.mapper.GroupMapper;
import com.neutron.youchat_backend.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    private GroupMapper groupMapper;

    //创建群聊
    @Override
    public Integer createGroup(Group group) {
        return groupMapper.createGroup(group);
    }

    //创建用户与群聊的对应关系
    @Override
    public Integer addUserGroup(Group group) {
        return groupMapper.addUserGroup(group);
    }

    //向群聊中添加用户
    @Override
    public Integer addUserInGroup(Integer groupId, Integer userId) {
        return groupMapper.addUserInGroup(groupId, userId);
    }

    //踢除群聊成员
    @Override
    public Integer deleteUserInGroup(List<UserGroupRelation> userGroupList) {
        return groupMapper.deleteUserInGroup(userGroupList);
    }

    //根据群id查询出该群信息
    @Override
    public Group queryGroupByGroupId(Integer groupId) {
        return groupMapper.queryGroupByGroupId(groupId);
    }

    //查找用户加的所有群组
    @Override
    public List<Group> queryAllGroupsUserJoined(Integer userId) {
        return groupMapper.queryAllGroupsUserJoined(userId);
    }

    //更新群聊成员数量
    @Override
    public Integer updateGroupNum(Integer groupId, Integer changeNum) {
        return groupMapper.updateGroupNum(groupId, changeNum);
    }
}
