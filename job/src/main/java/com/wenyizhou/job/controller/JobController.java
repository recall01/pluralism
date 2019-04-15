package com.wenyizhou.job.controller;

import com.google.gson.Gson;
import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private IJobService jobService;
    @RequestMapping("/findJob")
    public Response findJob(){
        return jobService.findJob();
    }
    @GetMapping("/jobList")
    public Response jobList(Integer page){
        return jobService.jobList(page);
    }
    @GetMapping("/jobListByTime")
    public Response jobListByTime(Integer time){
        return jobService.jobListByTime(time);
    }
    @GetMapping("/getJobById")
    public Response getJobById(String jobId){
        return jobService.getJobById(jobId);
    }
    @GetMapping("/jobListByJobType")
    public Response jobListByJobType(String type){
        return jobService.jobListByJobType(type);
    }
    @GetMapping("/getMaxPage")
    public Response getMaxPage(){
        return jobService.getMaxPage();
    }

    @PostMapping(value = "/delectJob",consumes = "application/x-www-form-urlencoded")
    public Response delectJob(@RequestParam String jobId){
        return jobService.delectJob(jobId);
    }

    @GetMapping("/getAppJobInfo")
    public Response getAppJobInfo(String jobId){
        return jobService.getAppJobInfo(jobId);
    }

    @PostMapping("/applyJob")
    public Response applyJob(@RequestParam String userId,@RequestParam String jobId){
        return jobService.applyJob(userId,jobId);
    }

}
