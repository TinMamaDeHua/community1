package com.nowcoder.community;

import com.nowcoder.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-12 14:55
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {
        String text = "这里可以读博,可以嫖娼,可以吸毒, 可以***...";
        text  =  sensitiveFilter.filter(text);
        System.out.println(text);

        text = "这里可以赌→博→,可以→嫖→娼→,可以吸→毒, 可以→***→...fabc";
        text  =  sensitiveFilter.filter(text);
        System.out.println(text);

        text = "fabcd";
        text  =  sensitiveFilter.filter(text);
        System.out.println(text);

        text = "fabcc";
        text  =  sensitiveFilter.filter(text);
        System.out.println(text);

        text = "fabc";
        text  =  sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
