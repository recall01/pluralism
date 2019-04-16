package com.wenyizhou.job.mapping;

import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.News;
import com.wenyizhou.job.model.VO.JobVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NewsMapping {

    void insertNews(News news);
}
