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
        return studentService.addIntroduction(introduction, userId);
    }

    @PostMapping(value = "/addJobType",consumes = "application/x-www-form-urlencoded")
    public Response addJobType(@RequestParam String jobType,@RequestParam String userId){
        return studentService.addJobType(jobType,userId);
    }

    @PostMapping(value = "/changeInfo",consumes = "application/x-www-form-urlencoded")
    public Response changeInfo(Student student){
        return studentService.changeInfo(student);
    }
    @PostMapping(value = "/delectFreeTime",consumes = "application/x-www-form-urlencoded")
    public Response delectFreeTime(String freId,String userId){
        return studentService.delectFreeTime(freId,userId);
    }
    @PostMapping(value = "/addFreeTime",consumes = "application/x-www-form-urlencoded")
    public Response addFreeTime(String startTime,String endTime,String stuId,String userId){
        return studentService.addFreeTime(startTime,endTime,stuId,userId);
    }
    @PostMapping(value = "/initStudentInfo",consumes = "application/x-www-form-urlencoded")
    public Response initStudentInfo(@RequestParam String userId){
        return studentService.initStudentInfo(userId);
    }
    @PostMapping(value = "/getMsg",consumes = "application/x-www-form-urlencoded")
    public Response getMsg(@RequestParam String userId){
        return studentService.getMsg(userId);
    }
    @PostMapping(value = "/delectMsg",consumes = "application/x-www-form-urlencoded")
    public Response delectMsg(@RequestParam String userId,@RequestParam String newsId){
        return studentService.delectMsg(userId,newsId);
    }
    @GetMapping(value = "/getStudentsInfo")
    public Response getStudentsInfo(@RequestParam Integer page){
        return studentService.getStudentsInfo(page);
    }
    @GetMapping(value = "/getStudentInfoById")
    public Response getStudentInfoById(@RequestParam String userId){
        return studentService.getStudentInfoById(userId);
    }
    @PostMapping(value = "/changeStudentInfo",consumes = "application/x-www-form-urlencoded")
    public Response changeStudentInfo(Student student){
        System.out.println(new Gson().toJson(student));
        return studentService.changeStudentInfo(student);
    }

    @GetMapping(value = "/getStudentPage")
    public Response getStudentPage(){
        return studentService.getStudentPage();
    }

    @GetMapping("/studentList")
    public Response studentList(Integer page){
        return studentService.studentList(page);
    }


}
