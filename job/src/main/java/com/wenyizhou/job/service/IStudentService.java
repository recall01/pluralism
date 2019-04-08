package com.wenyizhou.job.service;

import com.wenyizhou.job.model.Response;

public interface IStudentService {

    Response addIntroduction(String introduction,String userId);

    Response addJobType(String jobType, String userId);
}
