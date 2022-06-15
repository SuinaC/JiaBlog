package com.jia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jia.Exception.SystemException;
import com.jia.constants.SystemConstants;
import com.jia.domain.ResponseResult;
import com.jia.domain.entity.Comment;
import com.jia.domain.vo.CommentVo;
import com.jia.domain.vo.PageVo;
import com.jia.enums.AppHttpCodeEnum;
import com.jia.service.CommentService;
import com.jia.mapper.CommentMapper;

import com.jia.service.UserService;
import com.jia.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-04-14 17:26:27
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {

        //查询对应文章的根评论
        //对articleId进行判断
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
//        queryWrapper.eq(Comment::getArticleId,articleId);
        //根评论rootId为-1
        queryWrapper.eq(Comment::getRootId,-1);
        //评论类型
        queryWrapper.eq(Comment::getType,commentType);
        //分页查询
        Page<Comment> page=new Page(pageNum,pageSize);
        page(page,queryWrapper);
        List<CommentVo> commentVoList= toCommentVoList(page.getRecords());

        //查询所有根评论对应子评论并且赋值给对应属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应子评论
            List<CommentVo> children = getChildren(commentVo.getId());

            commentVo.setChildren(children);
        }
        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments=list(queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }


    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历Vo集合
        for (CommentVo commentVo : commentVos) {
            //通过createBy查询用户昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过toCommentUserId查询用户昵称并赋值，-1才进行查询
            if(commentVo.getToCommentUserId()!=-1){
                String ToCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(ToCommentUserName);
            }
        }

        return commentVos;
    }
}
