package com.wenyizhou.job.mapping;

import com.wenyizhou.job.model.AppJob;
import com.wenyizhou.job.model.VO.AppJobVO;
import com.wenyizhou.job.model.VO.UserJobLink;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppJobMapping {
    List<AppJobVO> selectApplicant(String jobId);

    AppJob selectAppJobById(Map m);

    void insertApplyJob(AppJob appJob);

    void delectAppJob(String jobId);

    void delectAppJobById(Map m);

    void updateJobStatus(Map m);

    List<UserJobLink> selectJobRecord(String userId);
}
