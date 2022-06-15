package com.jia.controller;

import com.jia.Exception.SystemException;
import com.jia.annotation.SystemLog;
import com.jia.domain.ResponseResult;
import com.jia.domain.entity.User;
import com.jia.enums.AppHttpCodeEnum;
import com.jia.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
//    @SystemLog(businessName = "登录")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }
    @PostMapping("/logout")
//    @SystemLog(businessName = "登出")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
