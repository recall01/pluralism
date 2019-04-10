package com.wenyizhou.job.mapping;

import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.JobType;
import com.wenyizhou.job.model.VO.JobVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface JobMapping {
    List<JobVO> selectJobList(Integer page);

    List<JobVO> selectjobListByTime(Map m);

    List<JobVO> selectjobListByJobType(String type);

    int selectMaxPage();
}
