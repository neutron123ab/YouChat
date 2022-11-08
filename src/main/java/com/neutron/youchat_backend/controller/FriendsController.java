package com.neutron.youchat_backend.controller;

import com.neutron.youchat_backend.common.Result;
import com.neutron.youchat_backend.entity.Friends;
import com.neutron.youchat_backend.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    @Autowired
    private FriendsService friendsService;

    //加好友
    @PostMapping("/addFriends")
    public Result<String> addFriends(@RequestBody Friends friends){
        if(friendsService.addFriends(friends) > 0){
            return Result.success();
        }
        return Result.error();
    }

    //删除好友
    @DeleteMapping("/deleteFriends")
    public Result<String> deleteFriends(@RequestBody Friends friends){
        if(friendsService.deleteFriends(friends) > 0){
            return Result.success();
        }
        return Result.error();
    }

    //查询好友
    @GetMapping
    public Result<List<Friends>> queryFriends(Integer userId){
        return Result.success(friendsService.queryFriendsByUserId(userId));
    }
}
