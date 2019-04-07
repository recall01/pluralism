package com.wenyizhou.job.controller;

import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @PostMapping(value = "/addIntroduction",consumes = "application/x-www-form-urlencoded")
    public Response addIntroduction(@RequestParam String introduction,@RequestParam String userId){
        return studentService.addIntroduction(introduction,userId);
    }
    @PostMapping(value = "/addJobType",consumes = "application/x-www-form-urlencoded")
    public Response addJobType(@RequestParam String jobType,@RequestParam String userId){
        return studentService.addJobType(jobType,userId);
    }
}
