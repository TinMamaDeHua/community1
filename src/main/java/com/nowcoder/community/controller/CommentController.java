package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.service.CommentService;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-14 9:34
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 添加完帖子的评论我们肯定是想回到帖子
     * 而回到帖子需要传帖子的id，所以我们在处理添加评论时，
     * 要求页面把帖子id传过来
     *
     * 老师说后面会做统一处理，但是统一处理是跳转到error页面，这对于用户体验是不友好的。我突然想到之前有一章讲了一个自定义注解
     *
     * 这里给addComment()方法加上这个注解，未登陆状态下企图评论就可以跳转到登录页面了，而不是跳转到error页面。
     * @param discussPostId
     * @param comment
     * @return
     */
    @LoginRequired
    @RequestMapping(path = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment) {
        // 这里可能会出现用户没登陆的情况
        // 我们会在后面用全局处理的异常类来处理；或给用户加权限，没登陆就访问不到该页面
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());

        commentService.addComment(comment);

        return "redirect:/discuss/detail/" + discussPostId;
    }
}
