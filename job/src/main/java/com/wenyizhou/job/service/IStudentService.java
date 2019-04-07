package com.wenyizhou.job.service;

import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;

public interface IStudentService {

    Response addIntroduction(String introduction,String userId);

    Response addJobType(String jobType, String userId);
}
