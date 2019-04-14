package com.wenyizhou.job.mapping;

import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.VO.AppJobVO;
import com.wenyizhou.job.model.VO.JobVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppJobMapping {
    AppJobVO selectApplicant(String jobId);
}
