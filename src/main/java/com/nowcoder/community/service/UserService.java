package com.nowcoder.community.service;

import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-6 14:15
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

}
