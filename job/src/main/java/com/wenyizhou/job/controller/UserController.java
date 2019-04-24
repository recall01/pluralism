package com.wenyizhou.job.controller;

import com.google.gson.Gson;
import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.service.IUserService;
import com.wenyizhou.job.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/register")
    public Response register(User user){
        return userService.register(user);
    }

    @PostMapping(value = "/login",consumes = "application/x-www-form-urlencoded")
    public Response login(@RequestParam String userPhone, @RequestParam String userPassword){
        return userService.login(userPhone,userPassword);
    }
    @PostMapping(value = "/userInfo",consumes = "application/x-www-form-urlencoded")
    public Response userInfo(@RequestParam String userId){
        return userService.userInfo(userId);
    }
    @PostMapping(value = "/changeInfo",consumes = "application/x-www-form-urlencoded")
    public Response changeInfo(User user){
        return userService.changeInfo(user);
    }
    @PostMapping(value = "/changeUserInfo",consumes = "application/x-www-form-urlencoded")
    public Response changeUserInfo(User user){
        return userService.changeUserInfo(user);
    }

    @PostMapping(value = "/delectUser",consumes = "application/x-www-form-urlencoded")
    public Response delectUser(@RequestParam String userId){
        return userService.delectUser(userId);
    }
    @GetMapping(value = "/exit")
    public Response exit(){
        return userService.exit();
    }
    @GetMapping(value = "/jobType")
    public Response jobType(){
        return userService.jobType();
    }

    @PostMapping(value = "/apply",consumes = "application/x-www-form-urlencoded")
    public Response apply(@RequestParam String userId){
        return userService.apply(userId);
    }
    @PostMapping(value = "/pubJob",consumes = "application/x-www-form-urlencoded")
    public Response pubJob(Job job){
        return userService.pubJob(job);
    }
    @GetMapping(value = "/getUserById")
    public Response getUserById(String userId){
        return userService.getUserById(userId);
    }
    @GetMapping(value = "/getUserInfoById")
    public Response getUserInfoById(@RequestParam String userId){
        return userService.getUserInfoById(userId);
    }
    @GetMapping(value = "/usersInfo")
    public Response usersInfo(@RequestParam Integer page){
        return userService.usersInfo(page);
    }
    @GetMapping(value = "/getApplies")
    public Response getApplies(@RequestParam Integer page){
        return userService.getApplies(page);
    }
    @PostMapping(value = "/agreeApply",consumes = "application/x-www-form-urlencoded")
    public Response agreeApply(@RequestParam String appId,@RequestParam String userId){
        return userService.agreeApply(appId,userId);
    }
    @PostMapping(value = "/deleteApply",consumes = "application/x-www-form-urlencoded")
    public Response deleteApply(@RequestParam String appId){
        return userService.deleteApply(appId);
    }
    @GetMapping(value = "/getUserPage")
    public Response getUserPage(){
        return userService.getUserPage();
    }
    @GetMapping(value = "/getApplyPage")
    public Response getApplyPage(){
        return userService.getApplyPage();
    }
}
