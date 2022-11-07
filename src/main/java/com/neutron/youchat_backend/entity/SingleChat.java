package com.neutron.youchat_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

//单人聊天
@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class SingleChat {

    private Integer id;

    //用户好友表id
    private Integer userFriendsId;

    //用户id
    private Integer userId;

    //好友id
    private Integer friendsId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //发送时间
    private String sendTime;

    //消息内容
    private String content;

}
