package com.wenyizhou.job.dao;

import com.wenyizhou.job.model.JobType;
import com.wenyizhou.job.model.Student;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.StudentVO;

import java.util.List;

public interface IUserDao {
    String register(User user) throws Exception;

    User login(String userPhone, String userPassword);

    User selectUserById(String userId);

    StudentVO selectStudentById(String userId);

    boolean changeInfo(User user);

    List<JobType> getJobType();
}
