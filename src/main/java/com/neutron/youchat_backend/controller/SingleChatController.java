package com.neutron.youchat_backend.controller;

import com.neutron.youchat_backend.common.Result;
import com.neutron.youchat_backend.entity.Friends;
import com.neutron.youchat_backend.entity.SingleChat;
import com.neutron.youchat_backend.service.SingleChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/singleChat")
public class SingleChatController {

    @Autowired
    private SingleChatService singleChatService;

    /**
     * 单聊发送消息
     *
     * @param singleChat 单聊信息（用户-好友关联表，信息内容）
     * @return 是否发送成功
     */
    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody SingleChat singleChat) {
        if (singleChatService.addMsgUserSend(singleChat) > 0) {
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 根据用户id和好友id获取用户-好友关联表id
     *
     * @param userId    用户id
     * @param friendsId 好友id
     * @return 用户-好友关联表id
     */
    @GetMapping("/getUserFriendsId")
    public Result<Integer> getUserFriendsId(Integer userId, Integer friendsId) {
        Friends friends = new Friends();
        friends.setUserId(userId);
        friends.setFriendsId(friendsId);
        Integer userFriendsId = singleChatService.queryUserFriendsId(friends);
        if (userFriendsId != null) {
            return Result.success(userFriendsId);
        }
        return Result.error();
    }

    /**
     * 根据用户-好友关联表的id获取聊天记录
     * @param userFriendsId 用户-好友关联表id
     * @return 聊天记录
     */
    @GetMapping("/getChatMsg")
    public Result<List<SingleChat>> getChatMsg(Integer userFriendsId){
        List<SingleChat> singleChatList = singleChatService.querySingleMsg(userFriendsId);
        if(singleChatList!=null){
            return Result.success(singleChatList);
        }
        return Result.error();
    }

}
