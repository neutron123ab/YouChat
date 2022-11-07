package com.neutron.youchat_backend.mapper;

import com.neutron.youchat_backend.entity.Friends;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendsMapper {

    //加好友
    Integer addFriends(Friends friends);

    //删除好友
    Integer deleteFriends(Friends friends);

    //查找好友
    List<Friends> queryFriendsByUserId(Integer userId);

}
