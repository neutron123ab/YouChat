package com.neutron.youchat_backend.mapper;

import com.neutron.youchat_backend.entity.Friends;
import com.neutron.youchat_backend.entity.SingleChat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SingleChatMapper {

    //用户发送消息，保存到数据库中
    Integer addMsgUserSend(SingleChat singleChat);

    //根据用户id和好友id获取好友表id
    Integer queryUserFriendsId(Friends friends);

    //根据用户好友表id获得用户聊天记录（按时间升序排序）
    List<SingleChat> querySingleMsg(Integer userFriendsId);



}
