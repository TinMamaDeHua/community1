package com.nowcoder.community.controller.advice;

import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 概要描述：加上@ControllerAdvice注解后，表示当前类是controller的全局配置类
 * 详细描述：我们要给注解加上扫描范围(默认会扫描所有类)，让该类只扫描带有controller的注解的类
 *          在该类中定义处理全局异常的方法(处理所有controller的异常就是处理全局异常)
 *          解释：因为服务端出现了异常，无非是在表现层、业务层、数据层这三层架构中，而数据层出现异常会抛给业务层，
 *          业务层出现异常会抛给表现层，所以我们在表现层处理异常就可以处理全局的异常。
 *
 * @author:程圣严 日期：2022-10-15 14:03
 */
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * 当出现异常时，异常会赋值给e，
     * 我们可以利用request和response来处理请求
     * @param e
     * @param request
     * @param response
     */
    @ExceptionHandler({Exception.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 日志记录异常信息
        // 这只是异常的概括信息
        logger.error("服务器发生异常：" + e.getMessage());
        // 记录异常详细的stack信息
        for (StackTraceElement element : e.getStackTrace()) {
            logger.error(element.toString());
        }
        /**
         * 在处理异常需要注意
         * 浏览器请求分为异步请求和普通请求，异步请求一般是想获取字符串,普通请求希望服务器返回网页
         * 所以我们需要针对不同的请求方式做不同的处理
         *
         * 我们可以通过对request域对象中的x-requested-with参数值做判断
         */
        String xRequestedWith = request.getHeader("x-requested-with");
        // 异步请求
        if ("XMLHttpRequest".equals(xRequestedWith)) {
            // 给浏览器响应普通字符串
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJSONString(1, "服务器发生异常!"));
        } else {
            // 若是普通请求，就重定向到500错误提示页
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }
}
