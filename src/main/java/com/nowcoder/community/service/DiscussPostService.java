package com.nowcoder.community.service;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-6 13:49
 */
@Service
public class DiscussPostService {

    @Autowired
    DiscussPostMapper discussPostMapper;

    public List<DiscussPost> getDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

}
