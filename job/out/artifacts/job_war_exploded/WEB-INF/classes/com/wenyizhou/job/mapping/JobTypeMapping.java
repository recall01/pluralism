package com.wenyizhou.job.mapping;

import com.wenyizhou.job.model.JobType;
import com.wenyizhou.job.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface JobTypeMapping {

    List<JobType> selectJobType();
}
