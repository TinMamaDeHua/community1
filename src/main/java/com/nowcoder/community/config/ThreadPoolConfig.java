package com.nowcoder.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 概要描述：要想让ThreadPoolTaskScheduler(Spring可执行定时任务的线程池)生效，需要加上几个注解
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-22 17:30
 */
@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}
