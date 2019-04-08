package com.wenyizhou.job.controller;

import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.service.IStudentService;
import com.wenyizhou.job.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {
    @Autowired
    private IStudentService studentService;



    @PostMapping(value = "/addIntroduction",consumes = "application/x-www-form-urlencoded")
    public Response addIntroduction(@RequestParam String introduction, @RequestParam String userId){
        return studentService.addIntroduction(introduction,userId);
    }
}
