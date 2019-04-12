package com.wenyizhou.job.dao.impl;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.wenyizhou.job.dao.IUserDao;
import com.wenyizhou.job.mapping.ApplyMapping;
import com.wenyizhou.job.mapping.JobTypeMapping;
import com.wenyizhou.job.mapping.StudentMapping;
import com.wenyizhou.job.mapping.UserMapping;
import com.wenyizhou.job.model.Apply;
import com.wenyizhou.job.model.JobType;
import com.wenyizhou.job.model.Student;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.StudentVO;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements IUserDao {
    @Resource
    UserMapping userMapping;
    @Resource
    StudentMapping studentMapping;
    @Resource
    JobTypeMapping jobTypeMapping;
    @Resource
    ApplyMapping applyMapping;

    @Override
    public String register(User user) throws Exception{
        try {
            userMapping.insert(user);
            return "注册成功";
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            return "插入数据已存在";
        }catch (Exception e){
            e.printStackTrace();
            return "注册失败,系统异常";
        }
    }

    @Override
    public User login(String userPhone, String userPassword) {
        Map m = new HashMap();
        m.put("userPhone",userPhone);
        m.put("userPassword",userPassword);
        return userMapping.select(m);
    }

    @Override
    public User selectUserById(String userId) {
        return userMapping.selectUserById(userId);
    }

    @Override
    public StudentVO selectStudentById(String userId) {
        return studentMapping.selectStudentById(userId);
    }

    @Override
    public boolean changeInfo(User user) {
        try {
            userMapping.updateInfo(user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<JobType> getJobType() {
        try {
            return jobTypeMapping.selectJobType();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean apply(Apply apply) {
        try {
            applyMapping.insertApply(apply);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
