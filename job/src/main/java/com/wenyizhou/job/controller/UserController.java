package com.wenyizhou.job.controller;

import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.service.IUserService;
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
        //return new ModelAndView("index");
        System.out.println(user.toString());
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


    @GetMapping(value = "/exit",consumes = "application/x-www-form-urlencoded")
    public Response exit(){
        return userService.exit();
    }
    @GetMapping(value = "/jobType")
    public Response jobType(){
        return userService.jobType();
    }
}
