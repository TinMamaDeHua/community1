package com.nowcoder.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 概要描述：日志类demo
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-6 20:27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CommunityApplication.class)
public class LoggerTests {

    /**
     * 创建一个日志类，LoggerFactory.getLogger(LoggerTests.class)，
     * 括号中的参数指定哪一个类为日志类
     */
    private static final Logger logger = LoggerFactory.getLogger(LoggerTests.class);


    @Test
    public void testLogger() {
        System.out.println(logger.getName());

        logger.debug("debug log");
        logger.info("info log");
        logger.warn("warn log");
        logger.error("error log");
    }


}
