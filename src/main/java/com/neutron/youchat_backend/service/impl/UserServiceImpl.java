package com.neutron.youchat_backend.service.impl;

import com.neutron.youchat_backend.common.RSAUtils;
import com.neutron.youchat_backend.entity.Role;
import com.neutron.youchat_backend.entity.User;
import com.neutron.youchat_backend.mapper.UserMapper;
import com.neutron.youchat_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名查询该用户是否存在
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.queryUserByUsername(username);
        if(user == null){
            throw new RuntimeException("用户不存在");
        }
        user.setRoles(userMapper.getRolesByUserId(user.getId()));

        return user;
    }

    /**
     * 注册添加用户
     * @param username 用户名
     * @param password 加密后的密码
     * @return 受影响行数
     */
    @Override
    public Integer addUser(String username, String password) {

        if(userMapper.addUser(username, password) > 0){
            return 1;
        }

        return 0;
    }

    /**
     * 对密码进行RSA解密
     * @param password 密文
     * @return 解密后的原密码
     */
    @Override
    public String decodePassword(String password) {
        RSAUtils rsaUtils = RSAUtils.getRsaUtils();
        return rsaUtils.decodePassword(password);
    }

    @Override
    public Integer getUserInfo(String username){
        return userMapper.queryUserByUserInfo(username);
    }
}
