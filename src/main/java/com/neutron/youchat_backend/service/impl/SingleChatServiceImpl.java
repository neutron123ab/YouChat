package com.neutron.youchat_backend.service.impl;

import com.neutron.youchat_backend.entity.Friends;
import com.neutron.youchat_backend.entity.SingleChat;
import com.neutron.youchat_backend.mapper.SingleChatMapper;
import com.neutron.youchat_backend.service.SingleChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingleChatServiceImpl implements SingleChatService {

    @Autowired
    private SingleChatMapper singleChatMapper;

    /**
     * 单人聊天用户发送信息
     * @param singleChat 聊天信息
     * @return 是否发送成功
     */
    @Override
    public Integer addMsgUserSend(SingleChat singleChat) {
        return singleChatMapper.addMsgUserSend(singleChat);
    }

    /**
     * 查询用户-好友关联表的id
     * @param friends 关联表信息
     * @return 关联id
     */
    @Override
    public Integer queryUserFriendsId(Friends friends) {
        return singleChatMapper.queryUserFriendsId(friends);
    }

    /**
     * 查询两个用户的所有聊天记录
     * @param userFriendsId 用户-好友关联表的id
     * @return 聊天记录
     */
    @Override
    public List<SingleChat> querySingleMsg(Integer userFriendsId) {
        return singleChatMapper.querySingleMsg(userFriendsId);
    }
}
