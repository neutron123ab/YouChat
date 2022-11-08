package com.neutron.youchat_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class Friends {

    private Integer id;

    //用户id
    private Integer userId;

    //好友id
    private Integer friendsId;

}
