package com.nowcoder.community.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 概要描述：封装的事件对象
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-18 10:09
 */
public class Event {
    /**
     * 当前处理的事件是什么(对于Kafka来说就是主题是什么)
     */
    private String topic;
    /**
     * 是谁来触发的事件
     */
    private int userId;
    /**
     * 这个人做的操作类型(实体类型：点赞、评论、关注)
     */
    private int entityType;
    private int entityId;
    /**
     * 实体的作者
     */
    private int entityUserId;
    /**
     * 事件对象是通用的，以后可能会出现别的业务，可能会有一些特殊的数据需要记录
     * 我们也不清楚具体是什么样的数据，所以我们多做一个map便于以后存放特殊的数据
     */
    private Map<String, Object> data = new HashMap<>();

    /**
     * 对set方法做一些改造，方便后面我们对成员变量进行修改
     */


    public String getTopic() {
        return topic;
    }

    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
