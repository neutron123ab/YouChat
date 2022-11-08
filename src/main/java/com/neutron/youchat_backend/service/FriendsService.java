package com.neutron.youchat_backend.service;

import com.neutron.youchat_backend.entity.Friends;

import java.util.List;

public interface FriendsService {

    //用户添加好友
    Integer addFriends(Friends friends);

    //用户删除好友
    Integer deleteFriends(Friends friends);

    //查找好友
    List<Friends> queryFriendsByUserId(Integer userId);

}
