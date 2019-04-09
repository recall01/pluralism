package com.wenyizhou.job.mapping;

import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.JobType;
import com.wenyizhou.job.model.VO.JobVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobMapping {
    List<JobVO> selectJobList(Integer page);
}
