package com.neutron.youchat_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

//群聊
@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class GroupChat {

    private Integer id;

    //用户-群组关联表id;
    private Integer userGroupId;

    //用户id
    private Integer userId;
    //用户名
    private String username;

    //群聊id
    private Integer groupId;
    //群聊名
    private String groupName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //发送时间
    private String sendTime;

    //消息内容
    private String content;

}
