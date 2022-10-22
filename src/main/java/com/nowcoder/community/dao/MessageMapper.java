package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-14 11:14
 */
@Mapper
public interface MessageMapper {

    /**
     * 查询当前用户的会话列表，针对每个会话只返回一条最新的私信
     */
    List<Message> selectConversations(int userId, int offset, int limit);

    /**
     * 查询当前用户的总会话数
     * @param userId
     * @return
     */
    int selectConversationCount(int userId);

    /**
     * 查询某个会话包含的私信列表（点击会话详情时需要）
     */
    List<Message> selectLetters(String conversationId, int offset, int limit);

    /**
     * 查询某个会话所包含的私信数量.
     * @param conversationId
     * @return
     */
    int selectLetterCount(String conversationId);

    /**
     * 查询未读私信的数量.
     * 注意这里有两种情况
     * 1、查询当前用户未读私信的数量
     * 2、查询当前用户某个会话中未读私信的数量
     * 所以这里为了实现2种业务，定义2个参数
     * @param userId
     * @param conversationId 该参数是动态拼接的，有就拼，
     * @return
     */
    int selectLetterUnreadCount(int userId, String conversationId);

    /**
     * 新增消息(发送私信)
     * @param message
     * @return
     */
    int insertMessage(Message message);


    /**
     * 修改消息的状态
     * @param ids
     * @param status
     * @return
     */
    int updateStatus(List<Integer> ids, int status);

    /**
     * 查询用户某主题中最新的那条通知
     * @param userId
     * @param topic
     * @return
     */
    Message selectLatestNotice(int userId, String topic);

    /**
     * 查询用户某主题的所有通知
     * @param userId
     * @param topic
     * @return
     */
    int selectNoticeCount(int userId, String topic);

    /**
     * 查询用户某主题的所有未读通知
     * @param userId
     * @param topic 当不传topic时，此时就是查询用户所有未读通知(页面展示需要)
     * @return
     */
    int selectNoticeUnreadCount(int userId, String topic);

    /**
     * 分页查询用户某主题的通知列表
     * @param userId
     * @param topic
     * @param offset
     * @param limit
     * @return
     */
    List<Message> selectNotices(int userId, String topic, int offset, int limit);
}
