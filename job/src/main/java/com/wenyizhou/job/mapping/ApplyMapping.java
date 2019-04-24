package com.wenyizhou.job.mapping;

import com.wenyizhou.job.model.Apply;
import com.wenyizhou.job.model.VO.ApplyVO;
import com.wenyizhou.job.model.VO.JobVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ApplyMapping {

    void insertApply(Apply apply);

    List<ApplyVO> selectApplies(Integer page);

    void delectApply(String appId);

    int selectApplyPage();
}
