package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-5 15:46
 */
@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    /**
     * 在访问这个方法时，前端控制器会给方法中的参数自动注入值
     * 底层的请求、响应操作
     *
     * @param request
     * @param response
     */
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求信息
        String method = request.getMethod();
        System.out.println(method);
        String path = request.getServletPath();
        System.out.println(path);
        //获取请求头信息,request.getHeaderNames()获取请求头中key组成的迭代器
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + "::" + value);
        }

        System.out.println(request.getParameter("code"));

        //给前端响应数据
        response.setContentType("text/html;charset=utf-8");
        /**
         * JDK7后，在try的括号中写流的信息，在编译的时候，会自动的加上finally，并且会在里面关闭流
         */
        try (
                PrintWriter writer = response.getWriter();
        ) {
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    // GET请求

    // /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    // POST请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    // 响应HTML数据

    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", 30);
        mav.setViewName("/demo/view");
        return mav;
    }

    /**
     * 这种是上一个方法的简写，当访问到此方法时，DispatcherServlet给model属性会注入Model对象
     * 把Model传递给了跳转的视图
     *
     * @param model
     * @return
     */
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", 80);
        return "/demo/view";
    }


    // 响应JSON数据(异步请求)
    // Java对象 -> JSON字符串 -> JS对象

    /**
     * 在访问此方法时，DispatcherServlet看到方法返回值是Map，会将Map对象转化为json对象返回给前端
     *
     * @return
     */
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        return emp;
    }

    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 24);
        emp.put("salary", 9000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 25);
        emp.put("salary", 10000.00);
        list.add(emp);

        return list;
    }

    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
        // 一个cookie只能储存一对key-value值
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        // 设置cookie的作用范围，不设置，浏览器每次请求都会带上cookie，这样会浪费网络资源(流量)
        cookie.setPath("/community/alpha");
        // 设置cookie的存活时间(单位：秒)
        cookie.setMaxAge(60 * 10);
        response.addCookie(cookie);

        return "set cookie";
    }

    /**
     * 当使用@CookieValue("code")注解后，服务端会从q请求头传过来的cookie中
     * 找到key为code对应的值，赋值给code参数
     *
     * @param code
     * @return
     */
    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "get cookie";
    }

    /**
     * 1、cookie是存储在浏览器的，若存储敏感数据(比如密码)，容易被导
     * 2、浏览器每次请求服务端都会带上cookie，会浪费网络资源(流量),对性能会造成一定影响
     */

    @RequestMapping(path = "/ajax", method = RequestMethod.POST)
    @ResponseBody
    public String testAjax(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return CommunityUtil.getJSONString(0, "操作成功!");
    }
}
