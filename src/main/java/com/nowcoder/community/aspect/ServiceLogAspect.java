package com.nowcoder.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 概要描述：对业务组件的日志切面AOP(即全局日志)
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-15 17:31
 */
@Component
@Aspect
public class ServiceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointCut() {

    }

    /**
     * 我们希望在调用方法前记录日志
     * @param joinPoint 增强的具体方法
     */
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        // 定义日志的格式
        // 用户[ip],在[什么时间],访问了[哪个包的哪个方法，比如com.nowcoder.community.service.xxx()].
        // 要获取用户的ip，需要request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteHost();
        // 格式化时间
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 获取方法的全限定名
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        logger.info(String.format("用户[%s],在[%s],访问了[%s].", ip, date, target));

    }
}
