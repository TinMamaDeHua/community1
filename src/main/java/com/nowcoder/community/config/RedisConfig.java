package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 概要描述：配置RedisTemplate
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-15 20:39
 */
@Configuration
public class RedisConfig {

    /**
     * Java要想访问Redis数据库需要连接，我们将它注入进来，spring容器会给该属性赋值
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        // 我们要想将Java对象存储到redis数据库中，需要指定key和value的序列化方式

        // 设置key(redis的key都是字符串)的序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.string());
        // 设置value的序列化方式,Java的对象有多种格式，我们这里序列化成json，
        redisTemplate.setValueSerializer(RedisSerializer.json());

        // 注意redis的特殊数据结构-hash，它本身也是key-value
        // 所以我们还要设置它的key-value的序列化方式

        // 设置hash的key的序列化方式
        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        // 设置hash的value的序列化方式
        redisTemplate.setHashValueSerializer(RedisSerializer.json());

        // 让设置的属性生效
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
