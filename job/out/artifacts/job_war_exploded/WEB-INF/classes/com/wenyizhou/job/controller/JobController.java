package com.wenyizhou.job.controller;

import com.google.gson.Gson;
import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private IJobService iJobService;
    @RequestMapping("/findJob")
    public Response findJob(){
        return iJobService.findJob();
    }
    @GetMapping("/jobList")
    public Response jobList(Integer page){
        return iJobService.jobList(page);
    }
    @GetMapping("/jobListByTime")
    public Response jobListByTime(Integer time){
        return iJobService.jobListByTime(time);
    }
    @GetMapping("/jobListByJobType")
    public Response jobListByJobType(String type){
        return iJobService.jobListByJobType(type);
    }
    @GetMapping("/getMaxPage")
    public Response getMaxPage(){
        return iJobService.getMaxPage();
    }
}
