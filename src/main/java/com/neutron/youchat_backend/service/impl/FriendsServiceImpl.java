package com.neutron.youchat_backend.service.impl;

import com.neutron.youchat_backend.entity.Friends;
import com.neutron.youchat_backend.mapper.FriendsMapper;
import com.neutron.youchat_backend.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    private FriendsMapper friendsMapper;

    /**
     * 添加好友
     * @param friends 好友信息
     * @return 返回值大于0即为添加成功
     */
    @Override
    public Integer addFriends(Friends friends) {
        return friendsMapper.addFriends(friends);
    }

    /**
     * 删除好友
     * @param friends 好友信息
     * @return 返回值大于0即为删除成功
     */
    @Override
    public Integer deleteFriends(Friends friends) {
        return friendsMapper.deleteFriends(friends);
    }

    /**
     * 根据用户id查询好友信息
     * @param userId 用户id
     * @return 好友信息
     */
    @Override
    public List<Friends> queryFriendsByUserId(Integer userId) {
        return friendsMapper.queryFriendsByUserId(userId);
    }
}
