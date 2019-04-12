package com.wenyizhou.job.service.impl;

import com.wenyizhou.job.dao.IStudentDao;
import com.wenyizhou.job.dao.IUserDao;
import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.Student;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.StudentVO;
import com.wenyizhou.job.service.IStudentService;
import com.wenyizhou.job.service.IUserService;
import com.wenyizhou.job.utils.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.beans.Transient;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private IUserService userService;
    @Autowired
    private IStudentDao studentDao;
    @Autowired
    private IUserDao userDao;
    private final static int RESPONSE_SUCCESS = 200;


    @Override
    public Response addIntroduction(String introduction,String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(introduction)||StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        //先修改数据
        if(!studentDao.addIntroduction(introduction,userId)){
            response.setError(ErrorCode.SYSTEM_EXCEPTION);
            return response;
        }
        //移除student
        if(httpServletRequest.getSession().getAttribute("student") != null){
            httpServletRequest.getSession().removeAttribute("student");
        }
        System.out.println("修改成功");
        //查询数据
        response = userService.userInfo(userId);
        if(response.getData() == null){
            response.setMsg("修改个人简历失败,无法查询到用户信息");
        }else {
            response.setMsg("修改个人简历成功");
        }
        return response;
    }

    @Override
    public Response addJobType(String jobType, String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(jobType)||StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        //修改兼职类型
        if(!studentDao.addJobType(jobType,userId)){
            response.setError(ErrorCode.SYSTEM_EXCEPTION);
            return response;
        }
        //移除缓存
        if(httpServletRequest.getSession().getAttribute("student") != null){
            httpServletRequest.getSession().removeAttribute("student");
        }
        //查询数据
        response = userService.userInfo(userId);
        if(response.getData() == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
        }else {
            response.setMsg("修改兼职类型成功");
        }
        return response;
    }

    @Override
    public Response changeInfo(Student student) {
        Response response = new Response();
        if(student == null){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        //修改兼职类型
        if(!studentDao.changeInfo(student)){
            response.setError(ErrorCode.SYSTEM_EXCEPTION);
            return response;
        }
        //移除缓存
        if(httpServletRequest.getSession().getAttribute("student") != null){
            httpServletRequest.getSession().removeAttribute("student");
        }
        //查询数据
        response = userService.userInfo(student.getUserId());
        if(response.getData() == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
        }else {
            response.setMsg("修改学生信息成功");
        }
        return response;
    }

    @Override
    public Response delectFreeTime(String freId,String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(freId)||StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        if(!studentDao.delectFreeTime(freId)){
            response.setError(ErrorCode.SYSTEM_EXCEPTION);
            return response;
        }
        //移除缓存this.removeAttribute();
        if(httpServletRequest.getSession().getAttribute("student") != null){
            httpServletRequest.getSession().removeAttribute("student");
        }
        //查询数据
        response = userService.userInfo(userId);
        if(response.getData() == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
        }else {
            response.setMsg("删除空闲时间段成功");
        }
        return response;
    }

    @Override
    public Response addFreeTime(String startTime, String endTime, String stuId,String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(startTime)||StringUtils.isEmpty(endTime)||StringUtils.isEmpty(stuId)||StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        if(!studentDao.addFreeTime(startTime,endTime,stuId)){
            response.setError(ErrorCode.SYSTEM_EXCEPTION);
            return response;
        }
        //移除缓存
        this.removeAttribute();
        //查询数据
        response = userService.userInfo(userId);
        if(response.getData() == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
        }else {
            response.setMsg("添加空闲时间段成功");
        }
        return response;
    }

    @Override
    @Transient
    public Response initStudentInfo(String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        //先根据uerId去学生表查询该用户信息是否已存在
        StudentVO student = userDao.selectStudentById(userId);
        if(student != null){
            response.setError(ErrorCode.DATA_ALREADY_EXIST);
            return response;
        }
        //如果学生表无该用户信息,则初始化数据
        Student stu = new Student();
        stu.setIntroduction("");
        stu.setSpecialty("");
        stu.setSalary("");
        stu.setJobType("");
        stu.setUserId(userId);
        //向学生表中插入数据
        if(!studentDao.initStudentInfo(stu)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        //移除缓存
        if(httpServletRequest.getSession().getAttribute("student") != null){
            httpServletRequest.getSession().removeAttribute("student");
        }
        //修改user表用户状态
        User user = new User();
        user.setUserId(userId);
        user.setRoleType(1);
        response = userService.changeInfo(user);
        if(response.getStatus() == 200){
            response.setMsg("初始化学生表成功");
        }
        return response;
    }

    //移除缓存
    private void removeAttribute(){
        if(httpServletRequest.getSession().getAttribute("student") != null){
            httpServletRequest.getSession().removeAttribute("student");
        }
    }
    //查询学生数据
    private Response getUser(String id){
        Response response = userService.userInfo(id);
        if(response.getData() == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
        }else {
            response.setMsg("修改学生信息成功");
        }
        return response;
    }
}
