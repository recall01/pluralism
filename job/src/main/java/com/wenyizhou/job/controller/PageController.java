package com.wenyizhou.job.controller;

import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.service.IUserService;
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
    @Autowired
    private IUserService userService;
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
}
