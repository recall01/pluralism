package com.wenyizhou.job.controller;

import com.wenyizhou.job.model.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class AdminController {
    @RequestMapping("/jobType")
    public Response index(){
        return null;
    }
    @RequestMapping("/jobSingle")
    public String jobSingle(){
        System.out.println("---jobSingle---");
        return "student_info";
    }
}
