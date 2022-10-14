package com.nowcoder.community.util;

import com.nowcoder.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * 概要描述：持有用户信息,用于代替session对象.
 * 详细描述：我们在保存用户时，要考虑多线程并发的情况(浏览器与服务器是多对一的情况，浏览器每发起一次请求
 *          ，服务器就会创建一个线程来处理这个请求)，所以不能单纯地把用户存在某个封装类的静态变量中
 *         在面对多线程时，我们想让线程隔离，使用ThreadLocal可以解决这个问题
 *
 * @author:程圣严 日期：2022-10-10 11:56
 */
@Component
public class HostHolder {

    public ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }
}
