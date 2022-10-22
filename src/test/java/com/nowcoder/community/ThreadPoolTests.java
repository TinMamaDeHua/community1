package com.nowcoder.community;

import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-22 16:57
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CommunityApplication.class)
public class ThreadPoolTests {

    @Autowired
    private AlphaService alphaService;

    /**
     * 记录日志，这样在打印日志时，会带上当前执行的线程池以及线程
     */
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolTests.class);

    // JDK普通线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    // JDK可执行定时任务的线程池
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    // Spring普通线程池
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    // Spring可执行定时任务的线程池
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    public void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 1.JDK普通线程池
     */
    @Test
    public void testExecutorService() {
        Runnable task = new Runnable() {

            @Override
            public void run() {
                logger.debug("hello ExecutorService");
            }
        };
        // 这样就会将线程池中处于空闲的线程拿来处理task任务
        for (int i = 0; i < 10; i++) {
            executorService.submit(task);
        }

        // 但是junit方法的线程它调用完方法，就关闭线程了，跟main线程不一样(主线程只要有程序还在执行，主线程就不会关闭)
        // 所以这里我们让测试方法的线程睡一会，让上面的逻辑能够顺利执行完
        sleep(10000);
    }

    /**
     * 2.JDK定时任务线程池
     */
    @Test
    public void testScheduledExecutorService() {
        Runnable task = new Runnable() {

            @Override
            public void run() {
                logger.debug("hello ScheduledExecutorService");
            }
        };
        // 这样就会将线程池中处于空闲的线程拿来定时处理task任务
        scheduledExecutorService.scheduleAtFixedRate(task, 10000, 1000, TimeUnit.MICROSECONDS);

        sleep(30000);

    }

    /**
     * 3.Spring普通线程池
     */
    @Test
    public void testThreadPoolTaskExecutor() {
        Runnable task = new Runnable() {

            @Override
            public void run() {
                logger.debug("hello ThreadPoolTaskExecutor");
            }
        };

        for (int i = 1; i < 10; i++) {
            taskExecutor.submit(task);
        }

        sleep(10000);
    }

    /**
     * 4.Spring可执行定时任务的线程池
     */
    @Test
    public void testThreadPoolTaskScheduler() {
        Runnable task = new Runnable() {

            @Override
            public void run() {
                logger.debug("hello ThreadPoolTaskExecutor");
            }
        };

        Date startTime = new Date(System.currentTimeMillis() + 10000);
        taskScheduler.scheduleAtFixedRate(task, startTime, 1000);

        sleep(30000);
    }

    /**
     * 5.Spring普通线程池(简化)
     */
    @Test
    public void testThreadPoolTaskExecutorSimple() {
        for (int i = 0; i < 10; i++) {
            alphaService.execute1();
        }

        sleep(10000);
    }

    /**
     * 6.Spring定时任务线程池(简化)
     */
    @Test
    public void testThreadPoolTaskSchedulerSimple() {
        sleep(30000);
    }
}
