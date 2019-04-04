package com.wenyizhou.job.controller;

import com.wenyizhou.job.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/job")
public class JobController {

    @RequestMapping("/findJob")
    public ModelAndView findJob(@RequestParam String jobName,@RequestParam String jobPlace){
        //return new ModelAndView("index");
        System.out.println(jobName+"  "+jobPlace);
        return new ModelAndView("redirect:/index");
    }
}
