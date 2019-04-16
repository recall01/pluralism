package com.wenyizhou.job.controller;

import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.service.IStudentService;
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
    @Autowired
    private IStudentService studentService;

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
        userService.register(user);
        return "/index";
    }
    @RequestMapping("/exit")
    public String exit(){
        if(httpServletRequest.getSession().getAttribute("user") != null){
            httpServletRequest.getSession().removeAttribute("user");
        }
        if(httpServletRequest.getSession().getAttribute("student") != null){
            httpServletRequest.getSession().removeAttribute("student");
        }
        if(httpServletRequest.getSession().getAttribute("verify") != null){
            httpServletRequest.getSession().removeAttribute("verify");
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
    @RequestMapping("/detailsJob")
    public String detailsJob(@RequestParam String jobId){
        if(StringUtils.isEmpty(jobId)){
            return "/index";
        }
        httpServletRequest.getSession().setAttribute("jobId",jobId);
        return "details_job";
    }
    @RequestMapping("/teacherInfo")
    public String teacherInfo(){
        //验证用户是否登录,并且是否为老师
        User user = (User)httpServletRequest.getSession().getAttribute("user");
        if(user == null){
            return "/index";
        }
        if(user.getRoleType() == 2){
            return "/teacher_info";
        }else if(user.getRoleType() == 1){
            return "/student_info";
        }else {
            return "/index";
        }
    }
    @GetMapping("/findJob")
    public String findJob(@RequestParam String jobName, @RequestParam String jobTypeName){
        if (StringUtils.isEmpty(jobName)||StringUtils.isEmpty(jobTypeName)){
            return "/index";
        }
        httpServletRequest.getSession().setAttribute("jobName",jobName);
        httpServletRequest.getSession().setAttribute("jobTypeName",jobTypeName);
        return "/find_job";
    }
    @GetMapping("/improveInfo")
    public String improveInfo(){
        //从session中拿用户登录凭证
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if(user == null){
            return "/index";
        }
        return "/improve_info";
    }
    @GetMapping("/applicantInfo")
    public String applicantInfo(@RequestParam String userId){
        if(StringUtils.isEmpty(userId)){
            return "/index";
        }
        httpServletRequest.getSession().setAttribute("applicantUserId",userId);
        userService.getUserById(userId);
        return "/applicant_info";
    }
    @GetMapping("/newsList")
    public String newsList(){
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if(user == null){
            return "/index";
        }
//        userService.getUserById(userId);
        Response response = studentService.getMsg(user.getUserId());
        if(response.getStatus()==200){
            return "/student_newslist";
        }else {
            return "/index";
        }

    }
}
