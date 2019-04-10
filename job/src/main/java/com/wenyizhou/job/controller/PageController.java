package com.wenyizhou.job.controller;

import com.wenyizhou.job.model.User;
import com.wenyizhou.job.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
public class PageController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    private IUserService userService;
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping("/studentInfo")
    public String jobSingle(){
        return "student_info";
    }
    @RequestMapping("/test")
    public String test(){
        return "test";
    }
    @RequestMapping("/register")
    public String register(User user){
        System.out.println(user.toString());
        userService.register(user);
        return "/index";
    }
    @RequestMapping("/exit")
    public String exit(){
        System.out.println("---exit---");
        if(httpServletRequest.getSession().getAttribute("user") != null){
            System.out.println("---user---");
            httpServletRequest.getSession().removeAttribute("user");
        }
        if(httpServletRequest.getSession().getAttribute("student") != null){
            System.out.println("---student---");
            httpServletRequest.getSession().removeAttribute("student");
        }
        return "/index";
    }
    @RequestMapping("/about")
    public String about(){
        return "about";
    }
    @RequestMapping("/contact")
    public String contact(){
        return "contact";
    }
    @RequestMapping("/jobList")
    public String joblist(){
        return "job_list";
    }
    @GetMapping("/findJob")
    public String findJob(@RequestParam String jobName, @RequestParam String jobTypeName){
        System.out.println(jobName+"  "+jobTypeName);
        if (StringUtils.isEmpty(jobName)||StringUtils.isEmpty(jobTypeName)){
            return "/index";
        }
        httpServletRequest.getSession().setAttribute("jobName",jobName);
        httpServletRequest.getSession().setAttribute("jobTypeName",jobTypeName);
        return "/find_job";
    }
}
