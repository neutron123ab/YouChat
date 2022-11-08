package com.neutron.youchat_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class Group {

    //组号
    private Integer id;

    //群主id
    private Integer groupOwnerId;

    //组名
    private String groupName;

    //群聊容量
    private Integer container;

    //加群人数
    private Integer num;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //群聊创建时间
    private String createTime;

}
