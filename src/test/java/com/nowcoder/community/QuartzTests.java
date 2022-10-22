package com.nowcoder.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-22 18:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class QuartzTests {
    /**
     * 利用调度器来执行某些操作(比如删除某个任务)
     */
    @Autowired
    private Scheduler scheduler;

    @Test
    public void testDeleteJob() {
        boolean res = false;
        try {
            res = scheduler.deleteJob(new JobKey("alphaJob", "alphaJobGroup"));
            System.out.println(res);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
