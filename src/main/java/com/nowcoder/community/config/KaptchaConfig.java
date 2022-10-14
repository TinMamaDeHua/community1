package com.nowcoder.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 概要描述：配置生成随机验证码的类
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-8 13:26
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public Producer kaptchaProducer() {
        /**
         * 由于验证码需要配置的属性较多，在配置文件中不太好写
         * 这里我们使用Properties来配置生成验证码所需的属性
         */
        Properties properties = new Properties();
        //设置图片宽度，高度
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "32");
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");
        //定义生成验证码的字符串范围
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        //生成随机字符的个数
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 使用哪个噪声类，就是在图片上加一些干扰，比如线、点、做3D、拉伸、阴影..防止机器人暴力破解
        // 但这种图片默认生成的就很好，默认就是防止破解的，本身就有点变形，所以就不加干扰
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");



        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
