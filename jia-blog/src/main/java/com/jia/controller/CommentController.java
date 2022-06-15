package com.jia.controller;

import com.jia.annotation.SystemLog;
import com.jia.constants.SystemConstants;
import com.jia.domain.ResponseResult;
import com.jia.domain.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jia.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @GetMapping("/commentList")
//    @SystemLog(businessName = "文章评论")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
//    @SystemLog(businessName = "添加评论")
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
//    @SystemLog(businessName = "友链评论")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT, null,pageNum,pageSize);
    }
}
