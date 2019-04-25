package com.wenyizhou.job.controller;

import com.google.gson.Gson;
import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.News;
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
    public Response jobList(Integer page,Integer count){
        return jobService.jobList(page,count);
    }
    @GetMapping("/jobListByTime")
    public Response jobListByTime(Integer time){
        return jobService.jobListByTime(time);
    }
    @GetMapping("/getJobById")
    public Response getJobById(String jobId){
        return jobService.getJobById(jobId);
    }
    @GetMapping("/getJobInfoById")
    public Response getJobInfoById(@RequestParam String jobId){
        return jobService.getJobInfoById(jobId);
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
    @PostMapping(value = "/delectJobById",consumes = "application/x-www-form-urlencoded")
    public Response delectJobById(@RequestParam String jobId){
        return jobService.delectJobById(jobId);
    }

    @GetMapping("/getAppJobInfo")
    public Response getAppJobInfo(String jobId){
        return jobService.getAppJobInfo(jobId);
    }

    @PostMapping(value = "/applyJob",consumes = "application/x-www-form-urlencoded")
    public Response applyJob(@RequestParam String userId,@RequestParam String jobId){
        return jobService.applyJob(userId,jobId);
    }
    @PostMapping(value = "/changeJob",consumes = "application/x-www-form-urlencoded")
    public Response changeJob(Job job){
        return jobService.changeJob(job);
    }
    @PostMapping(value = "/changeJobInfo",consumes = "application/x-www-form-urlencoded")
    public Response changeJobInfo(Job job){
        return jobService.changeJobInfo(job);
    }

    @PostMapping(value = "/agreeJob",consumes = "application/x-www-form-urlencoded")
    public Response agreeJob(News news){
        return jobService.agreeJob(news);
    }
    @PostMapping(value = "/rejectJob",consumes = "application/x-www-form-urlencoded")
    public Response rejectJob(News news){
        return jobService.rejectJob(news);
    }
    @GetMapping("/getJobsInfo")
    public Response getJobsInfo(@RequestParam Integer page){
        return jobService.getJobsInfo(page);
    }

    @PostMapping(value = "/addJobType",consumes = "application/x-www-form-urlencoded")
    public Response addJobType(@RequestParam String jobType){
        return jobService.addJobType(jobType);
    }

    @GetMapping(value = "/getJobPage")
    public Response getJobPage(){
        return jobService.getJobPage();
    }
    @PostMapping(value = "/closeJob",consumes = "application/x-www-form-urlencoded")
    public Response closeJob(@RequestParam String jobId){
        return jobService.closeJob(jobId);
    }
    @PostMapping(value = "/changeJobStatus",consumes = "application/x-www-form-urlencoded")
    public Response changeJobStatus(@RequestParam String jobId,@RequestParam Integer status){
        Response response = jobService.changeJobStatus(jobId,status);
        System.out.println(new Gson().toJson(response));
        return response;
    }
}
