package com.nowcoder.community.util;

/**
 * 概要描述：生成redis实体类型(帖子或评论)的key
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-16 10:53
 */
public class RedisKeyUtil {

    private static final String SPLIT = ":";
    /**
     * 某个实体(帖子和评论)的赞
     */
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    /**
     * 某个用户的赞
     */
    private static final String PREFIX_USER_LIKE = "like:user";
    /**
     * 关注的目标
     */
    private static final String PREFIX_FOLLOWEE = "followee";
    /**
     * 粉丝
     */
    private static final String PREFIX_FOLLOWER = "follower";
    private static final String PREFIX_KAPTCHA = "kaptcha";
    private static final String PREFIX_TICKET = "ticket";
    private static final String PREFIX_USER = "user";
    /**
     * 某个实体(帖子和评论)的赞
     * 格式为：like:entity:entityType:entityId -> set(userId)
     * 我们这里用set来存储赞了该实体的人的id集合,这样我们就可以通过实体的key获取那些人赞了这个实体
     * 这样也方便我们获取该实体赞的数量(set集合有size方法),也可以知道那些人赞了该实体，也方便应对后面业务的变化
     * 如果只用一个数字来存储实体赞的数量的话，我们后面就实现不了(哪些人赞了该实体)的业务
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * 某个用户的赞
     * 获取被赞的用户的key
     * like:user:userId --> int(用户被赞的数量)
     * @param userId
     * @return
     */
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }

    /**
     * 某个用户关注的实体
     * followee:userId:entityType --> zSet(entityId,now【时间】)
     * 这里以时间来作为分数是为了之后方便拓展业务(比如根据关注的时间来展示关注列表...)
     */
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }

    /**
     * 某个实体的粉丝(实体可能是人、帖子、题目等等，我们这里用的是人)
     * follower:entityType:entityId --> zSet(userId,now)
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getFollowerKey(int entityType ,int entityId) {
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    /**
     * 生成登录验证码的key
     * 我们需要识别当前登录的用户，所以我们在用户访问登录的时候给用户提供一个临时cookie凭证
     * @param owner 临时cookie凭证
     * @return
     */
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    /**
     * 登录凭证的key
     * @param ticket
     * @return
     */
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    /**
     * 用户
     * @param userId
     * @return
     */
    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }
}
