package com.wenyizhou.job.service.impl;

import com.google.gson.Gson;
import com.wenyizhou.job.dao.IUserDao;
import com.wenyizhou.job.model.*;
import com.wenyizhou.job.model.VO.ApplyVO;
import com.wenyizhou.job.model.VO.StudentVO;
import com.wenyizhou.job.model.VO.TeacherVO;
import com.wenyizhou.job.service.IUserService;
import com.wenyizhou.job.utils.ErrorCode;
import com.wenyizhou.job.utils.IDUtil;
import com.wenyizhou.job.utils.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.beans.Transient;
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
            return response;
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
        //封装id，registertime
        user.setUserId(IDUtil.getUserId());
        user.setRegisterTime(TimeUtil.getTime());
        if(StringUtils.isEmpty(user.getUserPassword())){
            user.setUserPassword("1234567a");
        }
        System.out.println(new Gson().toJson(user));
        if(StringUtils.isEmpty(user.getUserName())||StringUtils.isEmpty(user.getUserPhone())||StringUtils.isEmpty(user.getUserEmail())||StringUtils.isEmpty(user.getUserPassword())){
            response.setError(ErrorCode.PARAMETER_ERROR.getErrCode(),"入参字段不能为空");
            return response;
        }
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
                //response.setError(ErrorCode.DATA_NOT_EXIST);
                response.setStatus(RESPONSE_SUCCESS);
                response.setMsg("该用户还未验证");
                httpServletRequest.getSession().setAttribute("verify","还未验证");
                return response;
            case 1: //学生
                response = this.getStudentInfo(userId);
                break;
            case 2: //老师
                //查询老师发布的工作
                response = this.getTeacherInfo(userId);
                break;
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
        if(response.getStatus() == 200){
            response.setStatus(RESPONSE_SUCCESS);
            response.setMsg("修改用户基本成功");
        }
        return response;
    }

    @Override
    public Response exit() {
        if(httpServletRequest.getSession().getAttribute("user") != null){
        }
        if(httpServletRequest.getSession().getAttribute("student") != null){
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

    @Override
    public Response pubJob(Job job) {
        Response response = new Response();
        if(job == null){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        //封装job发布时间
        job.setPubTime(TimeUtil.getTime());
        //插入Job表
        if(!userDao.pubJob(job)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        //重新获取teacher信息
        this.getTeacherInfo(job.getUserId());
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("发布成功");
        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(userId)){
            userId = (String) httpServletRequest.getSession().getAttribute("applicantUserId");
            if(StringUtils.isEmpty(userId)){
                response.setError(ErrorCode.DATA_NOT_EXIST);
                return response;
            }
        }
        //根据userId查询user信息
        User applicantUser = userDao.selectUserById(userId);
        if(applicantUser == null){
            response.setError(ErrorCode.ACCOUNT_NOT_EXIST);
            return response;
        }
        //讲applicantUser对象存入session中
        httpServletRequest.getSession().setAttribute("applicantUser",applicantUser);
        response = this.getStudentInfo(userId);
        return response;
    }

    @Override
    public Response usersInfo(Integer page) {
        Response response = new Response();
        if(page==null||page == 0){
            page = 1;
        }
        page --;
        page = page * 10;
        List<User> users = userDao.getUsersInfo(page);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(users);
        response.setMsg("获取用户信息成功");
        return response;
    }

    @Override
    public Response getUserInfoById(String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        User user = userDao.selectUserById(userId);
        if(user == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
            return response;
        }
        System.out.println(new Gson().toJson(user));
        //处理密码数据
        user.setUserPassword(null);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(user);
        response.setMsg("获取用户信息成功");
        return response;
    }

    @Override
    public Response changeUserInfo(User user) {
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
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("修改成功");
        return response;
    }

    @Override
    public Response delectUser(String userId) {
        Response response = new Response();
        //先判断user.roleType
        if(StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        User user = userDao.selectUserById(userId);
        if(user == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
            return response;
        }
        if(user.getRoleType() !=2){
            //只删除user表中的信息
            if(!userDao.delectUser(userId)){
                response.setError(ErrorCode.SQL_OPERATING_FAIL);
                return response;
            }
        }
        //删除关联表中的消息
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("删除成功");
        return response;
    }

    @Override
    public Response getApplies(Integer page) {
        Response response = new Response();
        if(page == null||page == 0){
            page = 1;
        }
        page -- ;
        List<ApplyVO> applies = userDao.getApplies(page);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(applies);
        response.setMsg("获取消息列表成功");
        return response;
    }

    @Override
    @Transient
    public Response agreeApply(String appId, String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(appId)||StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        //删除apply表对应的数据
        if(!userDao.delectApply(appId)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        //修改user表中用户的信息
        User user = userDao.selectUserById(userId);
        if(user == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
            return response;
        }
        user.setRoleType(2);
        if(!userDao.changeInfo(user)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("执行成功");
        return response;
    }

    @Override
    public Response deleteApply(String appId) {
        Response response = new Response();
        if(StringUtils.isEmpty(appId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        //删除apply表对应的数据
        if(!userDao.delectApply(appId)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("删除消息成功");
        return response;
    }

    @Override
    public Response getUserPage() {
        Response response = new Response();
        int count = userDao.getUserPage();
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(count);
        response.setMsg("获取数据成功");
        return response;
    }

    @Override
    public Response getApplyPage() {
        Response response = new Response();
        int count = userDao.getApplyPage();
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(count);
        response.setMsg("获取数据成功");
        return response;
    }

    //获得学生信息
    private Response getStudentInfo(String userId){
        if(httpServletRequest.getSession().getAttribute("student") != null){
            httpServletRequest.getSession().removeAttribute("student");
        }
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
    //获取教师信息
    protected Response getTeacherInfo(String userId){
        if(httpServletRequest.getSession().getAttribute("teacher") != null){
            httpServletRequest.getSession().removeAttribute("teacher");
        }
        Response response = new Response();
        TeacherVO teacher = userDao.selectTeacherById(userId);
        if(teacher == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
            return response;
        }
        //循环遍历查询的jobs结果，名字为空的将其移除
        List<Job> jobs = teacher.getJobs();
        for (int i=0;i<jobs.size();i++){
            if(jobs.get(i).getJobName() == null){
                jobs.remove(i);
            }
        }
        if(jobs.size() == 0){
            teacher.setJobs(null);
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(teacher);
        httpServletRequest.getSession().setAttribute("teacher",teacher);
        return response;
    }
}
