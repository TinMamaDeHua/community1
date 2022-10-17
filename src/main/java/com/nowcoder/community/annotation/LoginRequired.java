package com.nowcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解来检查登录状态，虽然我们之前已经根据用户是否登录来决定
 * 是否显示页面的某些功能(消息，用户栏)，但是用户还是可以在地址栏手动输入不能访问的地址(比如访问个人设置页)，此时项目便是有漏洞的
 * 解决方案：
 *      我们通过在拦截器中判断，用户访问的方法上是否有注解，如果有就判断用户是否登录，若没登陆
 *      就重定向到登录页面，若登录，则放过请求
 * @author 86157
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

}
