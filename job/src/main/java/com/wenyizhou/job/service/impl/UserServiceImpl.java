package com.wenyizhou.job.service.impl;

import com.wenyizhou.job.dao.IUserDao;
import com.wenyizhou.job.model.*;
import com.wenyizhou.job.model.VO.StudentVO;
import com.wenyizhou.job.service.IUserService;
import com.wenyizhou.job.utils.ErrorCode;
import com.wenyizhou.job.utils.IDUtil;
import com.wenyizhou.job.utils.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private IUserDao userDao;
    private final static int RESPONSE_SUCCESS = 200;

    @Override
    public Response login(String userPhone, String userPassword) {
        //参数判空
        Response response = new Response();
        if(StringUtils.isEmpty(userPhone)||StringUtils.isEmpty(userPassword)||userPhone.length() != 11){
            response.setError(ErrorCode.PARAMETER_ERROR);
        }else {
            User user = userDao.login(userPhone,userPassword);
            //先移除verify缓存
            String verfy = (String) httpServletRequest.getSession().getAttribute("verify");
            if(verfy != null){
                httpServletRequest.getSession().removeAttribute("verify");
            }
            if (user.getRoleType() == 0){ //未验证
                httpServletRequest.getSession().setAttribute("verify","还未验证");
            }
            //将数据存入session
            if(user != null){
                httpServletRequest.getSession().setAttribute("user",user);
                response.setStatus(RESPONSE_SUCCESS);
                response.setData(user);
                response.setMsg("登录成功");
            }else {
                response.setError(ErrorCode.ACCOUNT_PASSWORD_ERROR);
            }
        }
        return response;
    }

    @Override
    public Response register(User user) {
        Response response = new Response();
        if(user == null){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        if(StringUtils.isEmpty(user.getUserName())||StringUtils.isEmpty(user.getUserPhone())||StringUtils.isEmpty(user.getUserEmail())||StringUtils.isEmpty(user.getUserPassword())){
            response.setError(ErrorCode.PARAMETER_ERROR.getErrCode(),"入参字段不能为空");
            return response;
        }
        //封装id，registertime
        user.setUserId(IDUtil.getUserId());
        user.setRegisterTime(TimeUtil.getTime());
        try {
            String result = userDao.register(user);
            if("注册成功".equals(result)){
                response.setStatus(RESPONSE_SUCCESS);
                response.setMsg("注册成功");
            }else {
                response.setError(ErrorCode.SQL_OPERATING_FAIL.getErrCode(),result);
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setData(e.getMessage());
            response.setStatus(3000);
        }

        return response;
    }

    @Override
    public Response userInfo(String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        //先在user表查询登录信息
        User user = userDao.selectUserById(userId);
        if(user == null){
            response.setError(ErrorCode.ACCOUNT_NOT_EXIST);
            return response;
        }
        //如果session没有user对象,存入session中
        if(httpServletRequest.getSession().getAttribute("user") == null){
            httpServletRequest.getSession().setAttribute("user",user);
        }
        /*//先移除verify缓存
        String verfy = (String) httpServletRequest.getSession().getAttribute("verify");
        if(verfy != null){
            httpServletRequest.getSession().removeAttribute("verify");
        }*/
        switch (user.getRoleType()){
            case 0: //未验证
                response.setError(ErrorCode.DATA_NOT_EXIST);
                httpServletRequest.getSession().setAttribute("verify","还未验证");
                return response;
            case 1: //学生
                response = this.getStudentInfo(userId);
                break;
            case 2:break;
            default:break;
        }
        return response;
    }

    @Override
    public Response changeInfo(User user) {
        Response response = new Response();
        if(user ==  null){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        //修改信息
        if(!userDao.changeInfo(user)){
            response.setError(ErrorCode.DATA_NOT_EXIST);
            return response;
        }
        //移除缓存
        if(httpServletRequest.getSession().getAttribute("user") != null){
            httpServletRequest.getSession().removeAttribute("user");
        }
        //查询数据
        response = this.userInfo(user.getUserId());
        if(response.getData() == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
        }else {
            response.setMsg("修改用户基本成功");
        }
        return response;
    }

    @Override
    public Response exit() {
        System.out.println("---user--exit1---");
        if(httpServletRequest.getSession().getAttribute("user") != null){
            httpServletRequest.getSession().removeAttribute("user");
        }
        if(httpServletRequest.getSession().getAttribute("student") != null){
            httpServletRequest.getSession().removeAttribute("student");
        }
        Response response = new Response();
        response.setStatus(RESPONSE_SUCCESS);
        return response;
    }

    @Override
    public Response jobType() {
        Response response = new Response();
        List<JobType> types = userDao.getJobType();
        if(types.size() == 0){
            response.setError(ErrorCode.DATA_NOT_EXIST);
        }else {
            response.setStatus(RESPONSE_SUCCESS);
            response.setData(types);
            response.setMsg("获取工作类型成功");
        }
        return response;
    }

    @Override
    public Response apply(String userId) {
        Response response = new Response();
        Apply apply = new Apply();
        if(StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        apply.setAppTime(TimeUtil.getTime());
        apply.setUserId(userId);
        apply.setAppType(0);
        if(!userDao.apply(apply)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("发送申请成功,等待审核");
        return response;
    }

    //获得学生信息
    private Response getStudentInfo(String userId){
        Response response = new Response();
        StudentVO student = userDao.selectStudentById(userId);
        if(student == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
            return response;
        }
        //循环遍历查询的freeTime结果，startTime或endTime为""将其移除
        List<FreeTime> times = student.getFreeTimes();
        for (int i=0;i<times.size();i++){
            if(StringUtils.isEmpty(times.get(i).getStartTime())||StringUtils.isEmpty(times.get(i).getEndTime())){
                student.getFreeTimes().remove(i);
            }
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(student);
        response.setMsg("查询成功");
        httpServletRequest.getSession().setAttribute("student",student);
        return response;
    }
}
