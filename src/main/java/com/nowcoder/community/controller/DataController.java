package com.nowcoder.community.controller;

import com.nowcoder.community.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * 概要描述：
 * 详细描述：
 *
 * @author:程圣严 日期：2022-10-22 12:08
 */
@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    /**
     * 跳转到统计页面
     * 因为在getUV()方法中将请求转发到了当前方法，而getUV()方法是post请求，
     * 请求转发的过程中，请求方式不能发生改变，所以设置该方法的访问方式为get和post(让该方法支持post请求)
     *
     * @return
     */
    @RequestMapping(path = "/data", method = {RequestMethod.GET, RequestMethod.POST})
    public String getDataPage() {
        return "/site/admin/data";
    }

    /**
     * 获取日期范围内的独立访客数量
     * 注意：页面传过来的是date类型的字符串，我们要告诉dispatcherServlet它的格式，
     * dispatcherServlet才能帮我们把字符串转为Date
     *
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(path = "/data/uv", method = RequestMethod.POST)
    public String getUV(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date end, Model model) {
        long uv = dataService.calculateUV(start, end);
        model.addAttribute("uvResult", uv);
        // 返回到统计页面，带上start和end给用户提供一个好的体验
        model.addAttribute("uvStartDate", start);
        model.addAttribute("uvEndDate", end);
        return "forward:/data";
    }

    /**
     * 获取日期范围内的活跃用户数量
     * @param start
     * @param end
     * @param model
     * @return
     */
    @RequestMapping(path = "/data/dau", method = RequestMethod.POST)
    public String getDAU(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                         @DateTimeFormat(pattern = "yyyy-MM-dd") Date end, Model model) {
        long dau = dataService.calculateDAU(start, end);
        model.addAttribute("dauResult", dau);
        // 返回到统计页面，带上start和end给用户提供一个好的体验
        model.addAttribute("dauStartDate", start);
        model.addAttribute("dauEndDate", end);
        return "forward:/data";
    }
}
