package com.wenyizhou.job.controller;

import com.wenyizhou.job.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {
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
}
