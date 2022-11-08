package com.neutron.youchat_backend.service.impl;

import com.neutron.youchat_backend.entity.GroupChat;
import com.neutron.youchat_backend.entity.UserGroupRelation;
import com.neutron.youchat_backend.mapper.GroupChatMapper;
import com.neutron.youchat_backend.service.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupChatServiceImpl implements GroupChatService {

    @Autowired
    private GroupChatMapper groupChatMapper;

    /**
     * 用户发送群聊消息
     * @param groupChat 群聊信息
     * @return 是否发送成功
     */
    @Override
    public Integer addMsgUserSendInGroup(GroupChat groupChat) {
        return groupChatMapper.addMsgUserSendInGroup(groupChat);
    }

    /**
     * 查询用户-群组关联表的id
     * @param userGroupRelation 用户-群组关联信息
     * @return 关联id
     */
    @Override
    public Integer queryUserGroupId(UserGroupRelation userGroupRelation) {
        return groupChatMapper.queryUserGroupId(userGroupRelation);
    }

    /**
     * 查询群聊信息
     * @param userGroupId 用户-群聊关联表id
     * @return 群聊信息
     */
    @Override
    public List<GroupChat> queryGroupMsg(Integer userGroupId) {
        return groupChatMapper.queryGroupMsg(userGroupId);
    }
}
