package com.wenyizhou.job.dao.impl;

import com.wenyizhou.job.dao.IJobDao;
import com.wenyizhou.job.dao.IUserDao;
import com.wenyizhou.job.mapping.JobMapping;
import com.wenyizhou.job.mapping.JobTypeMapping;
import com.wenyizhou.job.mapping.StudentMapping;
import com.wenyizhou.job.mapping.UserMapping;
import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.JobType;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.JobVO;
import com.wenyizhou.job.model.VO.StudentVO;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JobDaoImpl implements IJobDao {
    @Resource
    UserMapping userMapping;
    @Resource
    StudentMapping studentMapping;
    @Resource
    JobMapping jobMapping;

    @Override
    public List<JobVO> jobList(Integer page) {
        return jobMapping.selectJobList(page);
    }

    @Override
    public List<JobVO> jobListByTime(String startTime, String endTime) {
        try {
            Map m =new HashMap();
            m.put("startTime",startTime);
            m.put("endTime",endTime);
            return jobMapping.selectjobListByTime(m);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
