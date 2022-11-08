package com.neutron.youchat_backend.controller;

import com.neutron.youchat_backend.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketConnController {

    /**
     * 该接口用于在前端建立websocket连接之前执行认证操作
     * @return 验证是否通过，验证操作由后端过滤器进行
     */
    @GetMapping("/checkCertificate")
    public Result<String> checkCertificate(){
        return Result.success("check success");
    }

}
