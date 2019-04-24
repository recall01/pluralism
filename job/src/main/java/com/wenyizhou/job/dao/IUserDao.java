package com.wenyizhou.job.dao;

import com.wenyizhou.job.model.*;
import com.wenyizhou.job.model.VO.ApplyVO;
import com.wenyizhou.job.model.VO.StudentVO;
import com.wenyizhou.job.model.VO.TeacherVO;

import java.util.List;

public interface IUserDao {
    String register(User user) throws Exception;

    User login(String userPhone, String userPassword);

    User selectUserById(String userId);

    StudentVO selectStudentById(String userId);

    boolean changeInfo(User user);

    List<JobType> getJobType();

    boolean apply(Apply apply);

    TeacherVO selectTeacherById(String userId);

    boolean pubJob(Job job);

    List<User> getUsersInfo(Integer page);

    boolean delectUser(String userId);

    List<ApplyVO> getApplies(Integer page);

    boolean delectApply(String appId);

    int getUserPage();

    int getApplyPage();
}
