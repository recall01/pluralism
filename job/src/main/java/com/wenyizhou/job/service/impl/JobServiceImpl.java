package com.wenyizhou.job.service.impl;

import com.wenyizhou.job.dao.IJobDao;
import com.wenyizhou.job.model.*;
import com.wenyizhou.job.model.VO.AppJobVO;
import com.wenyizhou.job.model.VO.JobVO;
import com.wenyizhou.job.model.VO.TeacherVO;
import com.wenyizhou.job.service.IJobService;
import com.wenyizhou.job.utils.ErrorCode;
import com.wenyizhou.job.utils.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.beans.Transient;
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
    public Response jobList(Integer page,Integer count) {
        Response response = new Response();
        if(page == null||page == 0){
            page = 1;
        }
        if(count == null){
            count = 6;
        }
        page--;
        List<JobVO> jobs = jobDao.jobList(page,count);
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
            return this.jobList(1,6);
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
            return this.jobList(0,6);
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
        //获取userId,删除数据
        String userId = user.getUserId();
        if(!jobDao.delectJob(jobId,userId)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        //删除AppJob中的相关数据
        if(!jobDao.delectAppJob(jobId)){
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
    @Override
    public Response getJobInfoById(String jobId) {
        Response response = new Response();
        if(StringUtils.isEmpty(jobId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        Job job = jobDao.getJobInfoById(jobId);
        if(job == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
            return response;
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(job);
        response.setMsg("获取兼职信息成功");
        return response;
    }



    @Override
    public Response getAppJobInfo(String jobId) {
        Response response = new Response();
        //参数校验,如果为空,从session中拿jobId
        if(StringUtils.isEmpty(jobId)){
            jobId = (String) httpServletRequest.getSession().getAttribute("jobId");
            if(StringUtils.isEmpty(jobId)){
                response.setError(ErrorCode.PARAMETER_ERROR);
                return response;
            }
        }
        //根据jobId查询申请人数
        List<AppJobVO> appJobs = jobDao.getApplicantInfo(jobId);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(appJobs);
        response.setMsg("获取数据成功");
        return response;
    }

    @Override
    @Transient
    public Response applyJob(String userId, String jobId) {
        Response response = new Response();
        //1.校验数据
        if(StringUtils.isEmpty(userId)||StringUtils.isEmpty(jobId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        User user =(User) httpServletRequest.getSession().getAttribute("user");
        if(user == null){
            response.setError(ErrorCode.ACCOUNT_NOT_LOGIN);
            return response;
        }
        if(!user.getUserId() .equals(userId)){
            response.setError(ErrorCode.ACCOUNT_NOT_EXIST);
            System.out.println(user.getUserId() +"---"+userId);
            return response;
        }
        //2.在AppJob表中查询数据是否已经存在
        AppJob job = jobDao.getAppJobById(userId,jobId);
        if(job != null){
            response.setError(ErrorCode.DATA_ALREADY_EXIST.getErrCode(),"申请失败,已经申请过了");
            return response;
        }
        //3.封装数据
        AppJob appJob = new AppJob();
        appJob.setJobId(Integer.valueOf(jobId));
        appJob.setUserId(userId);
        appJob.setAppTime(TimeUtil.getTime());
        //4.向AppJob表中插入数据
        if(!jobDao.applyJob(appJob)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("申请工作成功");
        return response;
    }

    @Override
    public Response changeJob(Job job) {
        Response response = new Response();
        String jobId = (String) httpServletRequest.getSession().getAttribute("jobId");
        if(StringUtils.isEmpty(jobId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        job.setJobId(Integer.valueOf(jobId));
        if(!jobDao.changeJob(job)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("修改成功");
        return response;
    }
    @Override
    public Response changeJobInfo(Job job) {
        Response response = new Response();
        if(job == null){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        if(!jobDao.changeJob(job)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("修改成功");
        return response;
    }

    @Override
    public Response getJobPage() {
        Response response = new Response();
        int count = jobDao.getJobPage();
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(count);
        response.setMsg("获取数据成功");
        return response;
    }

    @Override
    @Transient
    public Response agreeJob(News news) {
        return this.handJob(news,1);
    }

    @Override
    public Response rejectJob(News news) {
        return this.handJob(news,2);
    }

    @Override
    public Response getJobsInfo(Integer page) {
        Response response = new Response();
        if(page == null){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        List<JobVO> jobs =jobDao.jobList(page,10);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(jobs);
        response.setMsg("获取兼职信息成功");
        return response;
    }

    @Override
    public Response delectJobById(String jobId) {
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
        //删除数据
        if(!jobDao.delectJob(jobId)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        //删除AppJob中的相关数据
        if(!jobDao.delectAppJob(jobId)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("删除兼职成功");
        return response;
    }

    @Override
    public Response addJobType(String jobType) {
        Response response = new Response();
        if(StringUtils.isEmpty(jobType)){
            response.setStatus(RESPONSE_SUCCESS);
            return response;
        }
        if(!jobDao.addJobType(jobType)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("添加工作类型成功");
        return response;
    }



    //处理操作兼职申请信息
    private Response handJob(News news,int type){
        Response response = new Response();
        if(news == null){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        //先根据Id获取job信息
        JobVO job = jobDao.getJobById(String.valueOf(news.getJobId()));
        if(job == null){
            response.setError(ErrorCode.DATA_NOT_EXIST);
            return response;
        }
        //封装数据
        news.setAddTime(TimeUtil.getTime());
        switch (type){
            case 1:
                news.setNewsType(1);
                news.setNewsInfo("你申请的["+job.getJobName()+"]已经被同意");break;
            case 2:
                news.setNewsType(2);
                news.setNewsInfo("你申请的["+job.getJobName()+"]已经被拒绝");break;
            default:break;
        }
        //添加数据到数据库
        if(!jobDao.agreeJob(news)){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        //干掉AppJob表中对应的数据
        if(!jobDao.delectAppJob(String.valueOf(news.getJobId()),news.getAccId())){
            response.setError(ErrorCode.SQL_OPERATING_FAIL);
            return response;
        }
        response.setStatus(RESPONSE_SUCCESS);
        response.setMsg("处理成功");
        return response;

    }

}
