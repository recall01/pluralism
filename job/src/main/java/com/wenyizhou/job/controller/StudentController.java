package com.wenyizhou.job.controller;

import com.google.gson.Gson;
import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.Student;
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
    public Response addIntroduction(String introduction,String userId){
        Response response = studentService.addIntroduction(introduction, userId);
        System.out.println(new Gson().toJson(response));
        return response;
    }

    @PostMapping(value = "/addJobType",consumes = "application/x-www-form-urlencoded")
    public Response addJobType(@RequestParam String jobType,@RequestParam String userId){
        return studentService.addJobType(jobType,userId);
    }

    @PostMapping(value = "/changeInfo",consumes = "application/x-www-form-urlencoded")
    public Response changeInfo(Student student){
        System.out.println(new Gson().toJson(student));
        return studentService.changeInfo(student);
    }
}
