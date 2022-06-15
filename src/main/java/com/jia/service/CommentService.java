package com.jia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jia.domain.ResponseResult;
import com.jia.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-04-14 17:26:27
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
