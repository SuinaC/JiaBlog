package com.jia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jia.domain.ResponseResult;
import com.jia.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-03-13 15:25:17
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
