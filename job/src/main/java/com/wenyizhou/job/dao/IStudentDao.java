package com.wenyizhou.job.dao;

import com.wenyizhou.job.model.News;
import com.wenyizhou.job.model.Student;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.StudentVO;

import java.util.List;

public interface IStudentDao {
    boolean addIntroduction(String introduction,String userId);

    boolean addJobType(String jobType, String userId);

    boolean changeInfo(Student student);

    boolean delectFreeTime(String freId);

    boolean addFreeTime(String startTime, String endTime, String stuId);

    boolean initStudentInfo(Student stu);

    List<News> getMsg(String userId);

    boolean delectMsg(String newsId);

    List<StudentVO> getStudentsInfo(Integer page);

    StudentVO getStudentInfoById(String stuId);

    boolean changeStudentInfo(Student student);

    int getStudentPage();
}
