package com.wenyizhou.job.dao;

import com.wenyizhou.job.model.*;
import com.wenyizhou.job.model.VO.*;

import java.util.List;

public interface IJobDao {
    List<JobVO> jobList(Integer page,Integer count);

    List<JobVO> jobListByTime(String startTime, String endTime);

    List<JobVO> jobListByJobType(String type);

    int getMaxPage();

    List<JobVO> findJob(String jobName);
    List<JobVO> findJob(String jobName,String jobTypeName);

    boolean delectJob(String jobId, String userId);
    boolean delectJob(String jobId);

    JobVO getJobById(String jobId);

    List<AppJobVO> getApplicantInfo(String jobId);

    AppJob getAppJobById(String userId, String jobId);

    boolean applyJob(AppJob appJob);

    boolean delectAppJob(String jobId);

    boolean delectAppJob(String jobId,String userId);

    boolean changeJob(Job job);

    boolean agreeJob(News news);

    boolean addJobType(String jobType);

    Job getJobInfoById(String jobId);

    int getJobPage();

    boolean changeJobStatus(String jobId, int status);

    boolean changeAppJobStatus(String jobId, String accId, int status);

    List<UserJobLink> getJobRecord(String userId);
}
