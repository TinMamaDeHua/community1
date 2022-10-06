package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-6 10:57
 */
@Mapper
public interface DiscussPostMapper {
    /**
     * 分页查询帖子
     *
     * @param userId 参数1、userId，是为了后面查看个人信息(发布的帖子数量)而添加的条件
     *               查询的时候，动态拼接SQL，判断userId(0的时候就不拼接)的值
     * @param offset 起始行行号
     * @param limit  每页查询多少条数据
     * @return
     */
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    /**
     * 查询总条数或某个用户的总帖子数
     * @Param 注解用于给参数取别名（当参数名很长时，可以在写sql时用该别名代替）,
     * 如果只有一个参数,并且在<if>里使用,则必须加别名.
     * @param userId
     * @return
     */
    int selectDiscussPostRows(@Param("userId") int userId);


}
