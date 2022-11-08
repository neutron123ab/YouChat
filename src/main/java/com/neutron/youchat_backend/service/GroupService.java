package com.neutron.youchat_backend.service;


import com.neutron.youchat_backend.entity.Group;
import com.neutron.youchat_backend.entity.UserGroupRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupService {

    //创建群聊
    Integer createGroup(Group group);

    //创建用户与群聊的对应关系
    Integer addUserGroup(Group group);

    //向指定群聊中添加用户
    Integer addUserInGroup(@Param("groupId") Integer groupId, @Param("userId") Integer userId);

    //将成员踢出群聊
    Integer deleteUserInGroup(List<UserGroupRelation> userGroupList);

    //根据群id查询出该群信息
    Group queryGroupByGroupId(Integer groupId);

    //查找用户加的所有群组
    List<Group> queryAllGroupsUserJoined(Integer userId);

    //更新群组表中的用户数量
    Integer updateGroupNum(@Param("groupId") Integer groupId, @Param("changeNum") Integer changeNum);

}
