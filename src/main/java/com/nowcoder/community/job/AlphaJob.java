package com.nowcoder.community.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 概要描述：定义一个执行任务
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-22 18:09
 */
public class AlphaJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(Thread.currentThread().getName() + " execute quartz job.");
    }
}
