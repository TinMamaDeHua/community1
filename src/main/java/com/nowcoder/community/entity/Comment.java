package com.nowcoder.community.entity;

import java.util.Date;

/**
 * 概要描述：评论表
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-13 14:50
 */
public class Comment {
    private int id;
    private int userId;
    /**
     * 实体类型：
     *      1--帖子
     *      2--评论
     */
    private int entityType;

    private int entityId;

    /**
     *  判断是否有回复的目标
     *  根据当前回复的targetId是否为0来判断，有2种情况
     *  1、targetId为0时，此时用户是直接去回复的评论，
     *  2、targetId为1时，用户去回复某个用户对当前帖子的评论
     */
    private int targetId;

    private String content;
    /**
     * 0-评论有效，1-评论无效
     */
    private int status;

    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", targetId=" + targetId +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
