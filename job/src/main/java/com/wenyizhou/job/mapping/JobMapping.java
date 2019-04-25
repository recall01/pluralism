package com.wenyizhou.job.mapping;

import com.wenyizhou.job.model.AppJob;
import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.VO.ApplyVO;
import com.wenyizhou.job.model.VO.JobVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface JobMapping {
    List<JobVO> selectJobList(Map m);

    List<JobVO> selectjobListByTime(Map m);

    List<JobVO> selectjobListByJobType(String type);

    int selectMaxPage();

    List<JobVO> selectJobByName(String jobName);

    List<JobVO> selectJobByNameAndType(Map m);

    void insertJob(Job job);

    void delectJob(Map m);

    JobVO selectJobById(String jobId);

    void updateJob(Job job);

    void delectJobById(String jobId);

    Job selectJobInfoById(String jobId);

    int selectJobPage();

    void updateJobStatus(Map m);
}
