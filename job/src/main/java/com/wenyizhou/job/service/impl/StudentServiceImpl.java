package com.wenyizhou.job.service.impl;

import com.google.gson.Gson;
import com.wenyizhou.job.dao.IJobDao;
import com.wenyizhou.job.dao.IStudentDao;
import com.wenyizhou.job.dao.IUserDao;
import com.wenyizhou.job.model.News;
import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.Student;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.JobVO;
import com.wenyizhou.job.model.VO.StudentVO;
import com.wenyizhou.job.model.VO.UserJobLink;
import com.wenyizhou.job.service.IStudentService;
import com.wenyizhou.job.service.IUserService;
import com.wenyizhou.job.utils.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.beans.Transient;
import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private IUserService userService;
    @Autowired
    private IStudentDao studentDao;
    @Autowired
    private IJobDao jobDao;
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

    @Override
    public Response getMsg(String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        List<News> newsList = studentDao.getMsg(userId);
        httpServletRequest.getSession().setAttribute("newsList",newsList);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(newsList);
        response.setMsg("获取消息列表成功");
        return response;
    }

    @Override
    public Response getJobRecord(String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        List<UserJobLink> jobRecords = jobDao.getJobRecord(userId);
        System.out.println(new Gson().toJson(jobRecords));
        httpServletRequest.getSession().setAttribute("jobRecords",jobRecords);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(jobRecords);
        response.setMsg("获取数据成功");
        return response;
    }

    @Override
    public Response studentList(Integer page) {
        Response response = new Response();
        if(page == null||page == 0){
            page = 1;
        }
        page--;
        List<StudentVO> students = studentDao.studentList(page);
        for (int i=0;i<students.size();i++){
            students.get(i).setFreeTimes(null);
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(students);
        response.setMsg("获取学生列表成功");
        return response;
    }

    @Override
    public Response delectMsg(String userId, String newsId) {
        Response response = new Response();
        if(StringUtils.isEmpty(userId)||StringUtils.isEmpty(newsId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if(user == null){
            response.setError(ErrorCode.ACCOUNT_NOT_LOGIN);
            return response;
        }
        if(!user.getUserId().equals(userId)){
            response.setError(ErrorCode.DATA_NOT_EXIST);
            return response;
        }
        if(!studentDao.delectMsg(newsId)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("删除消息成功");
        return response;
    }

    @Override
    public Response getStudentsInfo(Integer page) {
        Response response = new Response();
        if(page == 0){
            page = 1;
        }
        page --;
        page = page * 10;
        List<StudentVO> students = studentDao.getStudentsInfo(page);
        //处理空闲时间
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(students);
        response.setMsg("查询学生信息成功");
        return response;
    }

    @Override
    public Response getStudentInfoById(String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        if(userId.length()<10){
            for (int i=0;i<(10-userId.length());i++){
                userId = "0"+userId;
            }
        }
        StudentVO studentVO = studentDao.getStudentInfoById(userId);
        if(studentVO == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
            return response;
        }
        Student student = new Student();
        BeanUtils.copyProperties(studentVO,student);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(student);
        response.setMsg("查询学生信息成功");
        return response;
    }

    @Override
    public Response changeStudentInfo(Student student) {
        Response response = new Response();
        if(student == null){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        if(!studentDao.changeStudentInfo(student)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("修改学生信息成功");
        return response;
    }

    @Override
    public Response getStudentPage() {
        Response response = new Response();
        int count = studentDao.getStudentPage();
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(count);
        response.setMsg("获取数据成功");
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
