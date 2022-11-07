package com.neutron.youchat_backend.mapper;

import com.neutron.youchat_backend.entity.Group;
import com.neutron.youchat_backend.entity.UserGroupRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupMapper {

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

    //更新群组表中的用户数量
    Integer updateGroupNum(@Param("groupId") Integer groupId, @Param("changeNum") Integer changeNum);
}
