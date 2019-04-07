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

    @RequestMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @RequestMapping("/register")
    public ModelAndView register(User user){
        //return new ModelAndView("index");
        System.out.println(user.toString());
        return new ModelAndView("redirect:/index");
    }

    @PostMapping(value = "/login",consumes = "application/x-www-form-urlencoded")
    public Response login(@RequestParam String userPhone, @RequestParam String userPassword){
        System.out.println("---login---");
        return userService.login(userPhone,userPassword);
    }

}
