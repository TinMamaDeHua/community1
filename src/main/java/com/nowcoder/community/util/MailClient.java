package com.nowcoder.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 概要描述：在这里封装要发送到新浪服务器的内容，再由新浪帮我们把邮件发送到指定的地方
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-7 10:09
 */
@Component
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    /**
     * 使用JavaMailSender发送邮件
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送人，发给新浪服务器
     */
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 具体发送邮件的逻辑
     * @param to 在实际使用时，接收人是不同的，故需要定义,以下2参数同义
     * @param subject 发送的标题
     * @param content 发送的具体内容
     */
    public void sendMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // 使用MimeMessageHelper来构建邮件内容
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            // setText(content, true)，让邮件支持发送HTML格式的内容
            helper.setText(content, true);
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送邮件失败：" + e.getMessage());
        }

    }
}
