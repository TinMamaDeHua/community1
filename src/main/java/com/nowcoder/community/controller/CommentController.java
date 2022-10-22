package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Event;
import com.nowcoder.community.event.EventProducer;
import com.nowcoder.community.service.CommentService;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.util.CommunityConstant;
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
public class CommentController implements CommunityConstant {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private DiscussPostService discussPostService;

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

        // 触发评论事件
        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                // 通知详情页，如果评论的是帖子的话，可以跳转到帖子详情页(需要帖子id)
                .setData("postId", discussPostId);
        // 设置实体的作者，此时就需要根据实体类型判断
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            DiscussPost target = discussPostService.findDiscussPostById(discussPostId);
            event.setEntityUserId(target.getUserId());
        } else if (comment.getEntityType() == ENTITY_TYPE_COMMENT) {
            Comment target = commentService.findCommentById(comment.getId());
            event.setEntityUserId(target.getUserId());
        }
        // 发布通知
        eventProducer.fireEvent(event);

        // 触发发帖事件(如果是对帖子评论才触发，对帖子添加了评论后，需要修改帖子的评论数量)
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            event = new Event()
                    .setTopic(TOPIC_PUBLISH)
                    .setUserId(comment.getUserId())
                    .setEntityType(ENTITY_TYPE_POST)
                    .setEntityId(discussPostId);
            eventProducer.fireEvent(event);
        }


        return "redirect:/discuss/detail/" + discussPostId;
    }
}
