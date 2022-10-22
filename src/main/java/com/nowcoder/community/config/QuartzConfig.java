package com.nowcoder.community.config;

import com.nowcoder.community.job.AlphaJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * 配置 -> 数据库 -> 调用
 * 概要描述：spring容器启动时，第一次会读取该文件下的配置
 * 详细描述：将配置保存到数据库中，以后再调用定时任务就从数据库中取参数
 *
 * @author:程圣严 日期：2022-10-22 18:11
 */
@Configuration
public class QuartzConfig {

    // FactoryBean可简化Bean的实例化过程.
    // 1.通过FactoryBean封装Bean的实例化过程.
    // 2.将FactoryBean装配到Spring容器中
    // 3.将FactoryBean注入给其它的Bean
    // 4.该Bean得到的是FactoryBean所管理的对象实例

    /**
     * 配置JobDetail
     * @return
     */
//    @Bean
    public JobDetailFactoryBean alphaJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(AlphaJob.class);
        // 给job设置名称和组
        factoryBean.setName("alphaJob");
        factoryBean.setGroup("alphaJobGroup");
        // 设置任务长久保存(就算该任务的trigger被删掉，数据也仍会保留)
        factoryBean.setDurability(true);
        // 设置任务可恢复
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }

    /**
     * 有2种初始化配置Trigger(SimpleTriggerFactoryBean, CronTriggerFactoryBean)的方式
     * CronTriggerFactoryBean初始化的bean可以完成某些特殊时间段的任务(比如要在每月的最后一个星期五执行)
     * 配置cron表达式即可
     * @param alphaJobDetail
     * @return
     */
//    @Bean
    public SimpleTriggerFactoryBean alphaTrigger(JobDetail alphaJobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(alphaJobDetail);
        factoryBean.setName("alphaTrigger");
        factoryBean.setGroup("alphaTriggerGroup");
        // 设置job间隔多久执行一次
        factoryBean.setRepeatInterval(3000);
        // trigger底层要存储job的状态，用哪个对象来存储
        factoryBean.setJobDataMap(new JobDataMap());
        return factoryBean;
    }
}
