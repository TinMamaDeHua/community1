package com.nowcoder.community.service;

import com.google.code.kaptcha.Producer;
import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import com.nowcoder.community.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-6 14:15
 */
@Service
public class UserService implements CommunityConstant {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MailClient mailClient;

    @Autowired
    TemplateEngine templateEngine;

    /**
     * 前端获取服务器链接需要域名和项目路径
     * 我们把它们注入进来
     */
    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

//    @Autowired
//    LoginTicketMapper loginTicketMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Producer kaptchaProducer;

    /**
     * 每次请求都会被拦截器拦截，都会调用改方法，我们用redis来改善(将用户缓存到redis)，提高服务器的性能
     * @param id
     * @return
     */
    public User findUserById(int id) {
//        return userMapper.selectById(id);
        User user = getCache(id);
        if (user == null) {
            // 缓存中没有该用户
            user = initCache(id);
        }
        return user;
    }

    /**
     * 我们这里在处理注册时，可能会给前端返回：用户名已存在、密码为空、邮箱已被注册等等比较多的信息
     * 所以我们在这使用Map作为返回值类型来封装多个内容
     */
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();

        //空值处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空!");
            return map;
        }

        //验证账号
        User u = userMapper.selectByName(user.getUsername());
        if (u != null) {
            map.put("usernameMsg", "该账号已存在!");
            return map;
        }

        //验证邮箱
        u = userMapper.selectByEmail(user.getEmail());
        if (u != null) {
            map.put("emailMsg", "该邮箱已被注册!");
            return map;
        }

        //注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        //新注册用户肯定是普通用户，且为未激活状态
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        // 随机生成0-1000的数来替换%d，随机设置用户的头像
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)) );
        user.setCreateTime(new Date());
        userMapper.insertUser(user);


        //给当前用户的邮箱发送邮件，需要设置发送的邮箱以及激活的url
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        // 自定义激活码的url
        // http://localhost:8081/community/activation/用户id/code(验证码)
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);


        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "激活账号", content);

        return map;

    }

    /**
     * 处理用户点击激活邮件后的逻辑
     * @param userId
     * @param code
     * @return
     */
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (code.equals(user.getActivationCode())) {
            userMapper.updateStatus(userId, 1);
            clearCache(userId);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAIL;
        }
    }

    /**
     * 登录时也可能会出现多种错误的情况，比如账号为空、密码为空..
     * 为了处理多种情况，并给浏览器返回错误信息，我们使用map来作为返回值
     * @param username
     * @param password
     * @param expiredSeconds
     * @return
     */
    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();

        // 空值判断
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "账号不能为空!");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }

        // 验证账号
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "该账号不存在!");
            return map;
        }

        // 验证状态(用户是否已激活)
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "该账号未激活!");
            return map;
        }

        // 验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg", "密码错误!");
            return map;
        }

        // 生成登陆凭证，其实就是用登陆凭证来替换掉了session存登录信息
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
//        loginTicketMapper.insertLoginTicket(loginTicket);

        // 将登陆凭证存入redis中
        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        // 我们在RedisConfig里定义了value的序列化方式为json，当前loginTicket对象就会被序列化成json字符串存到redis中
        redisTemplate.opsForValue().set(redisKey, loginTicket);

        // 把凭证放到map中，前端登出后，再想登录，拦截器就可以根据凭证来查询凭证是否过期，决定用户是否能登陆..
        map.put("ticket", loginTicket.getTicket());


        return map;
    }

    /**
     * 将登录凭证修改为无效
     * @param ticket
     */
    public void logout(String ticket) {
//        loginTicketMapper.updateStatus(ticket, 1);
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        // 获取凭证对应的对象，修改状态，重新存入redis中
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(redisKey, loginTicket);
    }


    public int updateUserHeader(int userId, String headerUrl) {
        int rows = userMapper.updateHeader(userId, headerUrl);
        clearCache(userId);
        return rows;
    }

    public LoginTicket getByTicket(String ticket) {
//        return loginTicketMapper.selectByTicket(ticket);
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 重置密码
     */
    public Map<String, Object> resetPassword(String email, String password) {
        Map<String, Object> map = new HashMap<>();

        // 空值处理
        if (StringUtils.isBlank(email)) {
            map.put("emailMsg", "邮箱不能为空!");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }

        // 验证邮箱
        User user = userMapper.selectByEmail(email);
        if (user == null) {
            map.put("emailMsg", "该邮箱尚未注册!");
            return map;
        }

        // 重置密码
        password = CommunityUtil.md5(password + user.getSalt());
        userMapper.updatePassword(user.getId(), password);
        clearCache(user.getId());

        map.put("user", user);
        return map;
    }

    /**
     * 修改密码
     */
    public Map<String, Object> updatePassword(int userId, String oldPassword, String newPassword) {
        Map<String, Object> map = new HashMap<>();

        // 空值处理
        if (StringUtils.isBlank(oldPassword)) {
            map.put("oldPasswordMsg", "原密码不能为空!");
            return map;
        }
        if (StringUtils.isBlank(newPassword)) {
            map.put("newPasswordMsg", "新密码不能为空!");
            return map;
        }

        // 验证原始密码
        User user = userMapper.selectById(userId);
        oldPassword = CommunityUtil.md5(oldPassword + user.getSalt());
        if (!user.getPassword().equals(oldPassword)) {
            map.put("oldPasswordMsg", "原密码输入有误!");
            return map;
        }

        // 更新密码
        newPassword = CommunityUtil.md5(newPassword + user.getSalt());
        userMapper.updatePassword(userId, newPassword);
        clearCache(userId);

        return map;
    }

    /**
     * 发送验证码
     */
    public String sendKapacha(String content, String ticket) {
        LoginTicket loginTicket = getByTicket(ticket);
        User user = userMapper.selectById(loginTicket.getUserId());
        mailClient.sendMail(user.getEmail(), "修改密码", content);
        return user.getEmail();
    }

    /**
     * 自己写的修改密码
     * @param email
     * @param password
     * @return
     */
    public Map<String, Object> updateByEewPwdAndActivation(String email, String password) {
        Map<String, Object> map = new HashMap<>();

        // 空值判断
        if (StringUtils.isBlank(email)) {
            map.put("emailMsg", "邮箱不能为空!");
            return map;
        }
        // 空值判断
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }

        // 验证邮箱
        User user = userMapper.selectByEmail(email);
        if (user == null) {
            map.put("emailMsg", "邮箱不存在!");
            return map;
        }

        int res = userMapper.updatePassword(user.getId(), CommunityUtil.md5(password + user.getSalt()));
        if (res == 1) {
            map.put("success", "修改密码成功!");
            clearCache(user.getId());
        }



        return map;
    }

    public User findUserByName(String username) {
        return userMapper.selectByName(username);
    }

    /**
     * 1、优先从缓存中取值
     * @param userId
     * @return
     */
    public User getCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        return (User) redisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 2、在redis中取不到值时，初始化数据
     * @param userId
     * @return
     */
    public User initCache(int userId) {
        User user = userMapper.selectById(userId);
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.opsForValue().set(redisKey, user, 3600, TimeUnit.SECONDS);
        return user;
    }

    /**
     * 3、数据变更时清除缓存
     * @param userId
     */
    public void clearCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }

}
