package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-13 14:54
 */
@Mapper
public interface CommentMapper {

    /**
     * 评论：给帖子的评论
     * 回复：给评论的评论
     * 根据实体类型分页查询某条帖子的评论或评论的评论
     * @param entityType 实体类型
     * @param entityId
     * @param offset
     * @param limit
     * @return
     */
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    /**
     * 查询查询某条帖子或回复的评论总数
     * @param entityType
     * @param entityId
     * @return
     */
    int selectCountByEntity(int entityType, int entityId);


    int insertComment(Comment comment);
}
