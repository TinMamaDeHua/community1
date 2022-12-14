package com.nowcoder.community.config;

import com.nowcoder.community.controller.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 概要描述：拦截器的配置类
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-9 15:46
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 这个只是测试拦截器用法
     */
    /*@Autowired
    AlphaInterceptor alphaInterceptor;*/


    @Autowired
    LoginTicketInterceptor loginTicketInterceptor;

//    @Autowired
//    LoginRequiredInterceptor loginRequiredInterceptor;

    @Autowired
    MessageInterceptor messageInterceptor;

    @Autowired
    DataInterceptor dataInterceptor;

    /**
     * 不拦截静态资源
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(alphaInterceptor)
//                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg")
//                .addPathPatterns("/login", "/register");

        /**
         * 这里需要过滤所有请求，因为我们要根据user否存在来判断是否显示用户信息
         */
        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");

//        registry.addInterceptor(loginRequiredInterceptor)
//                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");

        registry.addInterceptor(messageInterceptor)
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");

        registry.addInterceptor(dataInterceptor)
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");
    }
}
