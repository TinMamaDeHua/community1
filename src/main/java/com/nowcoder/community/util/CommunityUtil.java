package com.nowcoder.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 概要描述：封装一些常用方法
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-7 13:04
 */
public class CommunityUtil {

    /**
     * 生成UUID
     * @return
     */
    public static String generateUUID() {
        // 去掉字符串中的-
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * MD5加密
     * hello -> abc123def456,若仅仅这样加密，也有可能被黑客破解（黑客那有常用的密码数据，比如生日，常用词...）
     * 故我们在给传入的密码再拼接一段随机字符串，即user表中的salt字段 提高密码的安全性
     * hello + 3e4a8 -> abc123def456abc
     * @param key
     * @return
     */
    public static String md5(String key) {
        //做一个判断，若key为null、空字符串、空格，就不做处理
        if (StringUtils.isBlank(key)) {
            return null;
        }
        //md5会将传入的字符串加密成16进制的字符串
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    /**
     * 给前端返回json字符串，可能包含编码、信息、数据
     * @param code
     * @param msg
     * @param map
     * @return
     */
    public static String getJSONString(int code, String msg, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if (map != null) {
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        return getJSONString(code, msg, null);
    }

    public static String getJSONString(int code) {
        return getJSONString(code, null, null);
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", 25);
        System.out.println(getJSONString(0, "ok", map));

    }

}
