package com.wenyizhou.job.service.impl;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wenyizhou.job.dao.IJobDao;
import com.wenyizhou.job.dao.IUserDao;
import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.JobType;
import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.JobVO;
import com.wenyizhou.job.model.VO.StudentVO;
import com.wenyizhou.job.model.VO.TeacherVO;
import com.wenyizhou.job.service.IJobService;
import com.wenyizhou.job.service.IUserService;
import com.wenyizhou.job.utils.ErrorCode;
import com.wenyizhou.job.utils.IDUtil;
import com.wenyizhou.job.utils.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JobServiceImpl implements IJobService {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private IJobDao jobDao;
    private final static int RESPONSE_SUCCESS = 200;


    @Override
    public Response jobList(Integer page) {
        Response response = new Response();
        if(page == null){
            page = 1;
        }
        page--;
        List<JobVO> jobs = jobDao.jobList(page);
        httpServletRequest.getSession().setAttribute("jobs",jobs);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(jobs);
        response.setMsg("获取工作列表成功");
        return response;
    }

    @Override
    public Response jobListByTime(Integer time) {
        Response response = new Response();
        if(time == null){
            time = 0;
        }
        Date date = new Date();
        String startTime = "";
        switch (time){
            case 0: startTime = TimeUtil.getLastOneDayTime(date);break;
            case 1: startTime = TimeUtil.getLastThreeDayTime(date);break;
            case 2: startTime = TimeUtil.getLastOneWeekTime(date);break;
            case 3: startTime = TimeUtil.getLastOneMonthTime(date);break;
            case 4: startTime = TimeUtil.getLastThreeMonthTime(date);break;
            default:break;
        }
        if("".equals(startTime)){
            return this.jobList(1);
        }
        String endTime = TimeUtil.getTime(date);
        List<JobVO> jobs = jobDao.jobListByTime(startTime,endTime);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(jobs);
        response.setMsg("获取工作列表成功");
        return response;
    }

    @Override
    public Response jobListByJobType(String type) {
        Response response = new Response();
        if(StringUtils.isEmpty(type)){
            return this.jobList(0);
        }
        List<JobVO> jobs = jobDao.jobListByJobType(type);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(jobs);
        response.setMsg("获取工作列表成功");
        return response;
    }
    //获取最大页数
    @Override
    public Response getMaxPage(){
        Response response = new Response();
        double v = 1.0 * jobDao.getMaxPage();
        int result = (int)Math.ceil(v/6);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(result);
        response.setMsg("获取最大页数成功");
        return response;
    }

    @Override
    public Response findJob() {
        Response response = new Response();
        String jobName = (String)httpServletRequest.getSession().getAttribute("jobName");
        String jobTypeName = (String)httpServletRequest.getSession().getAttribute("jobTypeName");
        if(StringUtils.isEmpty(jobName)||StringUtils.isEmpty(jobTypeName)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        List<JobVO> jobs;
        if(jobTypeName.contains("请选择")){
            jobs = jobDao.findJob(jobName);
        }else {
            jobs = jobDao.findJob(jobName,jobTypeName);
        }
        if(jobs != null){
            if(jobs.size()>0){
                httpServletRequest.getSession().setAttribute("findJobs",jobs);
            }
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(jobs);
        response.setMsg("查询工作成功");
        return response;
    }

    @Override
    public Response delectJob(String jobId) {
        Response response = new Response();
        if(StringUtils.isEmpty(jobId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        //校验登录信息
        User user =(User) httpServletRequest.getSession().getAttribute("user");
        if(user == null){
            response.setError(ErrorCode.ACCOUNT_NOT_LOGIN);
            return response;
        }
        //获取userId
        String userId = user.getUserId();
        if(!jobDao.delectJob(jobId,userId)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        //清除teacher缓存
        TeacherVO teacher =(TeacherVO) httpServletRequest.getSession().getAttribute("teacher");
        if(teacher != null){
            httpServletRequest.getSession().removeAttribute("teacher");
        }
        //重新查询数据
        response = userService.getTeacherInfo(userId);
        if(response.getStatus() == 200){
            response.setMsg("删除工作信息成功");
        }else {
            response.setError(ErrorCode.SYSTEM_EXCEPTION);
        }
        return response;
    }

    @Override
    public Response getJobById(String jobId) {
        Response response = new Response();
        //参数校验,如果为空,从session中拿jobId
        if(StringUtils.isEmpty(jobId)){
            jobId = (String) httpServletRequest.getSession().getAttribute("jobId");
            if(StringUtils.isEmpty(jobId)){
                response.setError(ErrorCode.PARAMETER_ERROR);
                return response;
            }
        }
        JobVO job = jobDao.getJobById(jobId);
        if(job == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
            return response;
        }
        //缓存Job
        httpServletRequest.getSession().setAttribute("job",job);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(job);
        response.setMsg("获取工作信息成功");
        return response;
    }


}
