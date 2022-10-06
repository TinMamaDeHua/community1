package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-5 13:53
 */
@Repository("alphaHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao{
    @Override
    public String select() {
        return "Hibernate";
    }
}
