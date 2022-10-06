package com.nowcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * 概要描述：当我们想从spring容器中获取AlphaDao的bean时，优先获取当前bean
 * 给当前bean加上@Primary注解即可
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-5 13:54
 */
@Repository
@Primary
public class AlphaDaoMybatisImpl implements AlphaDao{
    @Override
    public String select() {
        return "Mybatis";
    }
}
