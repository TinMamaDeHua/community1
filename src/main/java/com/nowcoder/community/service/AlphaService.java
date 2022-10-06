package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 概要描述：当一个类被设置为多实例时，只有spring容器在获取当前bean的时候才会初始化
 * 一般项目中都是bean都是单实例
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-5 14:33
 */
@Service
//@Scope("prototype")
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;

    public AlphaService() {
        System.out.println("AlphaService实例化");
    }

    /**
     * 初始化方法在对象实例化后调用
     */
    @PostConstruct
    public void init() {
        System.out.println("AlphaService初始化");
    }


    /**
     * 销毁方法在对象实例被销毁之前调用
     */
    @PreDestroy
    public void destroy() {
        System.out.println("AlphaService销毁");
    }

    public String find() {
        return alphaDao.select();
    }
}
