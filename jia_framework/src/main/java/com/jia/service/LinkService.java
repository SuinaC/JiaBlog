package com.jia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jia.domain.ResponseResult;
import com.jia.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-03-25 13:34:37
 */
public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();
}
