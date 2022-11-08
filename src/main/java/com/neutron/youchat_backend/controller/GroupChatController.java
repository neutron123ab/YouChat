package com.neutron.youchat_backend.controller;

import com.neutron.youchat_backend.common.Result;
import com.neutron.youchat_backend.entity.GroupChat;
import com.neutron.youchat_backend.entity.UserGroupRelation;
import com.neutron.youchat_backend.service.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groupChat")
public class GroupChatController {

    @Autowired
    private GroupChatService groupChatService;

    /**
     * 群聊发送消息
     * @param groupChat 群聊信息（用户-群组关联id，信息内容）
     * @return 是否发送成功
     */
    @PostMapping("/sendMsg")
    public Result<String> sendGroupChatMsg(@RequestBody GroupChat groupChat){
        if(groupChatService.addMsgUserSendInGroup(groupChat) > 0){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 根据用户id和群组id获取到用户-群组关联id
     * @param userId 用户id
     * @param groupId 群组id
     * @return 用户-群组关联id
     */
    @GetMapping("/getUserGroupId")
    public Result<Integer> getUserGroupId(Integer userId, Integer groupId){
        UserGroupRelation userGroupRelation = new UserGroupRelation();
        userGroupRelation.setUserId(userId);
        userGroupRelation.setGroupId(groupId);
        Integer userGroupId = groupChatService.queryUserGroupId(userGroupRelation);
        if(userGroupId!=null){
            return Result.success(userGroupId);
        }
        return Result.error();
    }

    /**
     * 获取群聊信息
     * @param userGroupId 用户-群聊关联id
     * @return 群聊信息
     */
    @GetMapping("/getGroupChatMsg")
    public Result<List<GroupChat>> getGroupChatMsg(Integer userGroupId){
        List<GroupChat> groupChatList = groupChatService.queryGroupMsg(userGroupId);
        if(groupChatList != null){
            return Result.success(groupChatList);
        }
        return Result.error();
    }

}
