package com.wenyizhou.job.dao;

import com.wenyizhou.job.model.Student;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.StudentVO;

public interface IStudentDao {
    boolean addIntroduction(String introduction,String userId);

    boolean addJobType(String jobType, String userId);

    boolean changeInfo(Student student);

    boolean delectFreeTime(String freId);

    boolean addFreeTime(String startTime, String endTime, String stuId);

    boolean initStudentInfo(Student stu);
}
