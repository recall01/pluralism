package com.wenyizhou.job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "student_info";
    }
}
