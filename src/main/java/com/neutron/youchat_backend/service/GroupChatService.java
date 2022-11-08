package com.neutron.youchat_backend.service;

import com.neutron.youchat_backend.entity.GroupChat;
import com.neutron.youchat_backend.entity.UserGroupRelation;

import java.util.List;

public interface GroupChatService {

    //用户发送消息，保存到数据库中
    Integer addMsgUserSendInGroup(GroupChat groupChat);

    //根据用户id和群组id获取用户群组关联表的id
    Integer queryUserGroupId(UserGroupRelation userGroupRelation);

    //根据用户群组关联表id获取群聊信息
    List<GroupChat> queryGroupMsg(Integer userGroupId);

}
