package com.wenyizhou.job.service;

import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.News;
import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;

public interface IJobService {
    Response jobList(Integer page);

    Response jobListByTime(Integer time);

    Response jobListByJobType(String type);

    Response getMaxPage();

    Response findJob();

    Response delectJob(String jobId);

    Response getJobById(String jobId);

    Response getAppJobInfo(String jobId);

    Response applyJob(String userId, String jobId);

    Response changeJob(Job job);

    Response agreeJob(News news);

    Response rejectJob(News news);
}
