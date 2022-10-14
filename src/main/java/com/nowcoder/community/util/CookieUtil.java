package com.nowcoder.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 概要描述：从request对象中根据key获取值
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-10 11:19
 */
public class CookieUtil {

    public static String getValue(HttpServletRequest request, String key) {
        if (request == null || key == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        Cookie[] cookies = request.getCookies();
        // 没登陆时，cookies就为null，注意要判断一下
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
