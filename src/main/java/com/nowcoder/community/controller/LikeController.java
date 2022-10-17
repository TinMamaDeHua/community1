package com.nowcoder.community.controller;

import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-16 12:14
 */
@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 点赞
     * 帖子详情页面发送过来的是异步请求(异步请求效率高，体验也好)，我们需要返回数据
     *
     * 改方法应该是登陆后才可以操作(后续老师将权限管理应该会做统一处理)，
     * 这里其实也可以给like方法加上自定义的 @LoginRequired注解来实现
     * @param entityType
     * @param entityId
     * @param entityUserId 被赞的用户id
     * @return
     */
    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId) {
        // 获取当前用户
        User user = hostHolder.getUser();

        // 点赞(或取消赞)
        likeService.like(user.getId(), entityType, entityId, entityUserId);
        // 获取实体的点赞数量
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        // 获取用户对实体的点赞状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);

        // 将数据封装到map中，返回结果
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        return CommunityUtil.getJSONString(0, null, map);
    }
}
