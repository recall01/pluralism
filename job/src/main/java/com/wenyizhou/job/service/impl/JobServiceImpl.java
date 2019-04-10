package com.wenyizhou.job.service.impl;

import com.wenyizhou.job.dao.IJobDao;
import com.wenyizhou.job.dao.IUserDao;
import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.JobType;
import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.JobVO;
import com.wenyizhou.job.model.VO.StudentVO;
import com.wenyizhou.job.service.IJobService;
import com.wenyizhou.job.service.IUserService;
import com.wenyizhou.job.utils.ErrorCode;
import com.wenyizhou.job.utils.IDUtil;
import com.wenyizhou.job.utils.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class JobServiceImpl implements IJobService {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private IJobDao jobDao;
    private final static int RESPONSE_SUCCESS = 200;


    @Override
    public Response jobList(Integer page) {
        Response response = new Response();
        if(page == null){
            page = 0;
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
            return this.jobList(0);
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


}
