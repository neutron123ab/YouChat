package com.neutron.youchat_backend.mapper;

import com.neutron.youchat_backend.entity.Resources;
import com.neutron.youchat_backend.entity.Role;
import com.neutron.youchat_backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    //根据用户名查找数据库中的用户
    User queryUserByUsername(String username);

    //注册添加用户
    Integer addUser(@Param("username") String username, @Param("password") String password);

    List<Role> getRolesByUserId(@Param("user_id") Integer user_id);

    List<Resources> getAllResources();

    //根据用户id查找用户信息
    User queryUserById(Integer id);

    //获取用户id
    Integer queryUserByUserInfo(String username);
}
