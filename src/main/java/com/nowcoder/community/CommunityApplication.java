package com.nowcoder.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这个类实际上是一个配置类，在运行此方法时，会扫描该类所在包以及其子包下的注解，把bean加入到spring容器中
 */
@SpringBootApplication
public class CommunityApplication {

    public static void main(String[] args) {
        /**
         * SpringApplication.run方法底层自动构建了spring容器
         * 内嵌了一个Tomcat
         */
        SpringApplication.run(CommunityApplication.class, args);
    }

}
