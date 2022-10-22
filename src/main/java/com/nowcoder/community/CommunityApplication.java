package com.nowcoder.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * 这个类实际上是一个配置类，在运行此方法时，会扫描该类所在包以及其子包下的注解，把bean加入到spring容器中
 */
@SpringBootApplication
public class CommunityApplication {

    /**
     * 解决netty启动冲突的问题
     * 冲突原因：redis底层是netty实现的，它已经设置好了处理器(availableProcessors为1)
     * 而ES底层也是依赖netty，在Netty4Utils的setAvailableProcessors也会去设置处理器
     * 调用NettyRuntime的setAvailableProcessors方法
     * 这就导致异常
     * (因为redis已经设置availableProcessors为1)，而availableProcessors不为0就会抛异常
     * 但debug源码发现，只要设置了es.set.netty.runtime.available.processors为false，Netty4Utils中的
     * setAvailableProcessors方法就直接返回，所以我们在程序的入口设置该属性，当配置类实例化后马上初始化该属性的值
     * 就解决了问题
     */
    @PostConstruct
    public void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    public static void main(String[] args) {
        /**
         * SpringApplication.run方法底层自动构建了spring容器
         * 内嵌了一个Tomcat
         */
        SpringApplication.run(CommunityApplication.class, args);
    }

}
