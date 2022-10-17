package com.nowcoder.community.service;

import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-16 11:54
 */
@Service
public class LikeService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 点赞
     * 我们这里不仅要实现点赞功能，还要记录当前被赞的用户的点赞数量
     * 这两个操作是关联的，我们用redis的编程式事务来实现
     * @param userId 点赞肯定是当前用户，所以要将用户id传进来
     * @param entityType
     * @param entityId
     * @param entityUserId 这里不要根据entityType去mysql数据库查询出用户
     *                     (还要判断实体类型啥的，比较麻烦)
     *                     这样也会降低性能,所以要求页面传过来   被赞的用户的id
     */
    public void like(int userId, int entityType, int entityId, int entityUserId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);
                // 判断当前实体是否已被点赞过(注意在事务外查询)
                boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);
                // 开启事务
                operations.multi();

                if (isMember) {
                    // 若已点赞，则取消点赞,被赞用户的点赞数量-1
                    operations.opsForSet().remove(entityLikeKey, userId);
                    operations.opsForValue().decrement(userLikeKey);
                } else {
                    operations.opsForSet().add(entityLikeKey, userId);
                    operations.opsForValue().increment(userLikeKey);
                }

                return operations.exec();
            }
        });
    }

    /**
     * 查询某个实体的点赞数量
     * @param entityType
     * @param entityId
     * @return
     */
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    /**
     * 查询某人对某实体的点赞状态
     * 这里的返回值我们用整数，是为了之后方便拓展业务需求，
     * 比如之后我们要实现拉踩的功能，如果用布尔就只能表示2种情况
     * @param userId
     * @param entityType
     * @param entityId
     * @return 若点过赞，返回1，反之返回0
     */
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }

    /**
     * 获取某个用户的赞
     * @param userId
     * @return
     */
    public int findUserLikeCount(int userId) {
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count == null ? 0 : count;
    }
}
