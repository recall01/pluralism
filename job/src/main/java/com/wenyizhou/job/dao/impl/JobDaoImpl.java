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
        page = page * 6;
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

    @Override
    public List<JobVO> jobListByJobType(String type) {
        try {
            return jobMapping.selectjobListByJobType(type);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getMaxPage() {
        try {
            return jobMapping.selectMaxPage();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<JobVO> findJob(String jobName) {
        try {
            return jobMapping.selectJobByName(jobName);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<JobVO> findJob(String jobName, String jobTypeName) {
        try {
            Map m =new HashMap();
            m.put("jobName",jobName);
            m.put("jobTypeName",jobTypeName);
            return jobMapping.selectJobByNameAndType(m);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delectJob(String jobId, String userId) {
        try {
            Map m =new HashMap();
            m.put("jobId",jobId);
            m.put("userId",userId);
            jobMapping.delectJob(m);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
