package com.neutron.youchat_backend.controller;

import com.neutron.youchat_backend.common.Result;
import com.neutron.youchat_backend.entity.User;
import com.neutron.youchat_backend.service.JwtTokenService;
import com.neutron.youchat_backend.service.UserService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    /**
     * 注册账号
     * @param user 用户信息
     * @return 是否注册成功
     */
    @PostMapping("/signup")
    public Result<String> signup(@RequestBody User user){
        String password = userService.decodePassword(user.getPassword());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        Integer integer = userService.addUser(user.getUsername(), encodedPassword);
        if(integer > 0){
            return Result.success("signup success");
        }
        return Result.error("signup failed");
    }

    /**
     * 登录接口
     * @param user  包含用户名和密码
     * @return jwt字符串
     */
    @PostMapping
    public Result<String> login(@RequestBody User user) throws JOSEException {

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(token);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        //登录成功，返回JWT字符串
        RSAKey rsaKey = jwtTokenService.generateRsaKey();
        String key = UUID.randomUUID().toString();

        User userInfo = (User)authenticate.getPrincipal();
        //用户信息3小时过期
        redisTemplate.opsForValue().set(key, userInfo, 3, TimeUnit.HOURS);

        return Result.success(jwtTokenService.generateTokenByRSA(key, rsaKey));
    }

    /**
     * 根据用户名获取用户id
     * @param username 用户名
     * @return 用户id
     */
    @GetMapping("/getUserInfo")
    public Result<Integer> getUserInfo(String username){
        return Result.success(userService.getUserInfo(username));
    }

}
