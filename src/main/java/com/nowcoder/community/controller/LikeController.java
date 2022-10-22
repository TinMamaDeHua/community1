package com.nowcoder.community.controller;

import com.nowcoder.community.entity.Event;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.event.EventProducer;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.util.CommunityConstant;
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
public class LikeController implements CommunityConstant {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    /**
     * 点赞
     * 帖子详情页面发送过来的是异步请求(异步请求效率高，体验也好)，我们需要返回数据
     *
     * 该方法应该是登陆后才可以操作(后续老师做权限管理应该会做统一处理)，
     * 这里其实也可以给like方法加上自定义的 @LoginRequired注解来实现
     * @param entityType
     * @param entityId
     * @param entityUserId 被赞的用户id
     * @param postId 要求点赞时把帖子id传过来，方便发送点赞通知(点赞通知详情页需要帖子id)
     * @return
     */
    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId, int postId) {
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

        // 触发点赞事件
        // 如果当前是点赞我才通知(取消赞也通知别人，就有点恶心人了)
        if (likeStatus == 1) {
            Event event = new Event()
                    .setTopic(TOPIC_LIKE)
                    .setUserId(user.getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId)
                    // 要求点赞时把帖子id传过来，方便发送点赞通知(点赞通知详情页需要帖子id)
                    .setData("postId", postId);

            // 点赞通知
            eventProducer.fireEvent(event);
        }

        return CommunityUtil.getJSONString(0, null, map);
    }
}
