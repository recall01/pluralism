package com.wenyizhou.job.dao.impl;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.wenyizhou.job.dao.IUserDao;
import com.wenyizhou.job.mapping.*;
import com.wenyizhou.job.model.*;
import com.wenyizhou.job.model.VO.ApplyVO;
import com.wenyizhou.job.model.VO.StudentVO;
import com.wenyizhou.job.model.VO.TeacherVO;
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
    @Resource
    JobMapping jobMapping;

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

    @Override
    public TeacherVO selectTeacherById(String userId) {
        try {
            return userMapping.selectTeacherById(userId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean pubJob(Job job) {
        try {
            jobMapping.insertJob(job);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getUsersInfo(Integer page) {
        try {
            return userMapping.selectUsersInfo(page);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delectUser(String userId) {
        try {
            userMapping.delectUser(userId);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ApplyVO> getApplies(Integer page) {
        try {
            return applyMapping.selectApplies(page);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delectApply(String appId) {
        try {
            applyMapping.delectApply(appId);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getUserPage() {
        try {
            return userMapping.selectUserTotal();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getApplyPage() {
        try {
            return applyMapping.selectApplyPage();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
