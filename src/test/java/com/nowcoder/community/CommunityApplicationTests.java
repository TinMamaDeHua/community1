package com.nowcoder.community;


import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;

/**
 * 指定CommunityApplication类作为当前类的配置类
 * 如果一个类想得到spring容器，可以实现ApplicationContextAware接口，
 * 在spring容器扫描组件的时候，会扫到这个bean，调用这个bean的set方法，把自身传进来
 *
 * 通过这些讲解，我们不难发现，有了spring后，我们把bean的实例化以及bean的注入都交给了spring容器去管理
 * 提高了我们开发代码的效率，也降低了我们开发者与代码的耦合度
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CommunityApplication.class)
public class CommunityApplicationTests implements ApplicationContextAware {

    /**
     * 在运行test方法时，spring的容器会被注入进来
     */
    ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void testApplicationContext() {
        System.out.println(applicationContext);

        AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
        System.out.println(alphaDao.select());

        alphaDao = applicationContext.getBean("alphaHibernate", AlphaDao.class);
        System.out.println(alphaDao.select());
    }

    @Test
    public void testBeanManagement() {
        AlphaService alphaService = applicationContext.getBean(AlphaService.class);
        System.out.println(alphaService);

        alphaService = applicationContext.getBean(AlphaService.class);
        System.out.println(alphaService);
    }

    @Test
    public void testBeanConfig() {

        SimpleDateFormat simpleDateFormat = applicationContext.getBean(SimpleDateFormat.class);
        System.out.println(simpleDateFormat);
    }

    /**
     * 如果我们要给当前的属性注入指定的bean
     * 加上@Qualifier注解，填写你要注入的bean的name
     *
     * 像当前这种情况，当前的bean依赖的是接口，底层的实现是不跟它直接耦合的，降低了耦合度
     * 而且很方便，不需要你去实例化
     */
    @Autowired
    @Qualifier("alphaHibernate")
    private AlphaDao alphaDao;


    @Autowired
    private AlphaService alphaService;

    @Autowired
    private SimpleDateFormat simpleDateFormat;

    /**
     * 我们之前演示的都是主动地去spring容器去获取bean
     * 是为了与spring通过依赖注入获取bean做对比.
     * 通过DI获取bean非常简单
     * 给你要注入bean的属性加上@Autowired即可
     */
    @Test
    public void testDI() {
        System.out.println(alphaDao);
        System.out.println(alphaService);
        System.out.println(simpleDateFormat);
    }


}
