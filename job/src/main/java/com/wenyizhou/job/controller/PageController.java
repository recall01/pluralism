package com.wenyizhou.job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
public class PageController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @RequestMapping("/index")
    public String index(Model model){
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
    @RequestMapping("/jobList")
    public String joblist(){
        return "job_list";
    }
}
