package com.wenyizhou.job.service;

import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;

public interface IUserService {
    Response login(String userPhone, String userPassword);

    Response register(User user);

    Response userInfo(String userId);

    Response changeInfo(User user);

    Response exit();

    Response jobType();

    Response apply(String userId);

    Response pubJob(Job job);

    Response getUserById(String userId);

    Response usersInfo(Integer page);

    Response getUserInfoById(String userId);

    Response changeUserInfo(User user);

    Response delectUser(String userId);

    Response getApplies(Integer page);

    Response agreeApply(String appId, String userId);

    Response deleteApply(String appId);

    Response getUserPage();

    Response getApplyPage();

    User selectUserByphone(String userPhone);
}
