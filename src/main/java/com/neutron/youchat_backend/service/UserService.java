package com.neutron.youchat_backend.service;

import com.neutron.youchat_backend.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    //注册添加用户
    Integer addUser(String username, String password);

    //对前端传过来的密码进行RSA解密
    String decodePassword(String password);

    Integer getUserInfo(String username);
}
