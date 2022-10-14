package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-7 10:31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {

    @Autowired
    MailClient mailClient;

    /**
     * 在测试环境下，我们需要主动使用模板引擎去访问模板
     */
    @Autowired
    TemplateEngine templateEngine;

    @Test
    public void testSendMail() {
        mailClient.sendMail("1798939708@qq.com", "TEST", "第一次发送邮件");
    }

    @Test
    public void testHtmlMail() {
        //访问模板，需要传参，就需要使用context对象
        Context context = new Context();
        context.setVariable("username", "程圣严");
        // 设置访问哪一个模板引擎，并传入设置的参数
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("1798939708@qq.com", "HTML", content);
    }
}
