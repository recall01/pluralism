package com.wenyizhou.job.service.impl;

import com.wenyizhou.job.dao.IStudentDao;
import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.service.IStudentService;
import com.wenyizhou.job.utils.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private IStudentDao studentDao;
    private final static int RESPONSE_SUCCESS = 200;


    @Override
    public Response addIntroduction(String introduction,String userId) {
        Response response = new Response();
        if(StringUtils.isEmpty(introduction)||StringUtils.isEmpty(userId)){
            response.setError(ErrorCode.PARAMETER_ERROR);
            return response;
        }
        if(studentDao.addIntroduction(introduction,userId)){
            response.setStatus(RESPONSE_SUCCESS);
            response.setMsg("修改个人简历成功");
        }else {
            response.setError(ErrorCode.SYSTEM_EXCEPTION);
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
        if(studentDao.addJobType(jobType,userId)){
            response.setStatus(RESPONSE_SUCCESS);
            response.setMsg("修改期待兼职类型成功");
        }else {
            response.setError(ErrorCode.SYSTEM_EXCEPTION);
        }
        return response;
    }
}
