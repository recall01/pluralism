package com.wenyizhou.job.service.impl;

import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.service.IUserService;
import com.wenyizhou.job.utils.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Response login(String userPhone, String userPassword) {
        //参数判空
        Response response = new Response();
        if(StringUtils.isEmpty(userPhone)||StringUtils.isEmpty(userPassword)||userPhone.length() != 11){
            response.setError(ErrorCode.PARAMETER_ERROR);
        }else {
            User user = new User();
            user.setUserName("文一舟");
            user.setUserPhone(userPhone);
            user.setUserPassword(userPassword);
            //将数据存入session
            httpServletRequest.getSession().setAttribute("user",user);
            response.setStatus(200);
        }
        return response;
    }
}
