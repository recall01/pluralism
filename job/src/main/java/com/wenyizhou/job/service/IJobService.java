package com.wenyizhou.job.service;

import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;

public interface IJobService {
    Response jobList(Integer page);

    Response jobListByTime(Integer time);

    Response jobListByJobType(String type);
}
