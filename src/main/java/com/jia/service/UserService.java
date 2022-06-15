package com.jia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jia.domain.ResponseResult;
import com.jia.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-04-14 17:53:39
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}
