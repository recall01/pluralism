package com.wenyizhou.job.dao;

import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.JobType;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.JobVO;
import com.wenyizhou.job.model.VO.StudentVO;

import java.util.List;

public interface IJobDao {
    List<JobVO> jobList(Integer page);

    List<JobVO> jobListByTime(String startTime, String endTime);
}
