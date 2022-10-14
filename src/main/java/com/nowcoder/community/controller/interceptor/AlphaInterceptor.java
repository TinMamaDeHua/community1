package com.nowcoder.community.controller.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 概要描述：自定义拦截器
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-9 15:42
 */
@Component
public class AlphaInterceptor implements HandlerInterceptor {


    private static final Logger logger = LoggerFactory.getLogger(AlphaInterceptor.class);

    /**
     * 在controller之前调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("preHandle：" + handler.toString());
        return true;
    }

    /**
     * 在controller之后调用
     *
     * @param request
     * @param response
     * @param handler 具体拦截的方法（方法名）
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("postHandle：" + handler.toString());
    }

    /**
     * 在模板引擎之后调用
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("afterCompletion：" + handler.toString());
    }
}
