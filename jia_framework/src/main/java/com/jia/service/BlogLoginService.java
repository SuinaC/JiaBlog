package com.jia.service;

import com.jia.domain.ResponseResult;
import com.jia.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login (User user);

    ResponseResult logout();
}
