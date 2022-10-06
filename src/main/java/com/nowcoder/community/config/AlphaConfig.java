package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * 概要描述：@Bean 可以装配任何Bean 无论是第三方的Bean，还是自定义的Bean，都可以
 *
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-5 14:49
 */
@Configuration
public class AlphaConfig {

    /**
     * 这里的意思：将方法的返回值交给spring容器管理
     * 这个bean的名字就是方法名
     * @return
     */
    @Bean
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat();
    }
}
