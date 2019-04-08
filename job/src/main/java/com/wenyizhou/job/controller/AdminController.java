package com.wenyizhou.job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping("/index")
    public String index(Model model){
        return "index";
    }
    @RequestMapping("/jobSingle")
    public String jobSingle(){
        System.out.println("---jobSingle---");
        return "job_single";
    }
}
