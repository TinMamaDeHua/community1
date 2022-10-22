package com.nowcoder.community.dao.elasticsearch;

import com.nowcoder.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 概要描述：通过这个类，该类继承ElasticsearchRepository接口后，我们就可以向es服务器进行crud的操作
 * 详细描述：ElasticsearchRepository接口封装了对es服务器crud的方法
 *          需要指定泛型，key的泛型：指定ElasticsearchRepository处理的实体类是哪个
 *                      value的泛型：指定实体类的主键
 *
 * @author:程圣严 日期：2022-10-19 14:36
 */
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {

}
