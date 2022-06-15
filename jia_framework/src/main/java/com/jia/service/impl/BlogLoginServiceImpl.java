package com.jia.service.impl;

import com.jia.domain.ResponseResult;
import com.jia.domain.entity.LoginUser;
import com.jia.domain.entity.User;
import com.jia.domain.vo.BlogUserLoginVo;
import com.jia.domain.vo.UserInfoVo;
import com.jia.service.BlogLoginService;
import com.jia.utils.BeanCopyUtils;
import com.jia.utils.JwtUtil;
import com.jia.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户或密码错误");
        }
        //获取userid，生成token
        LoginUser loginUser= (LoginUser) authenticate.getPrincipal();
        String userid = String.valueOf(loginUser.getUser().getId());
        String jwt = JwtUtil.createJWT(userid);
        //把用户信息存入redis
        redisCache.setCacheObject("bloglogin:"+userid,loginUser);
        //把token和userinfo封装
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin:"+userId);
        return ResponseResult.okResult();
    }
}
