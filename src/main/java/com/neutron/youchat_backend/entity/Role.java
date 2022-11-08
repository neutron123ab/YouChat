package com.neutron.youchat_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    private Integer id;
    private String name;    //角色名
    private String nameZh;  //角色中文名
    private List<Permission> permissions;  //角色所具有的权限

}
