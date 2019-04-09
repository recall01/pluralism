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
        List<JobVO> jobs = jobDao.jobList(page);
        httpServletRequest.getSession().setAttribute("jobs",jobs);
        response.setStatus(RESPONSE_SUCCESS);
        response.setData(jobs);
        response.setMsg("获取工作列表成功");
        return response;
    }
}
