package com.wenyizhou.job.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AdminController {
    @RequestMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("index");
    }
}
