package com.wenyizhou.job.service;

import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.Student;

public interface IStudentService {

    Response addIntroduction(String introduction,String userId);

    Response addJobType(String jobType, String userId);

    Response changeInfo(Student student);

    Response delectFreeTime(String freId,String userId);

    Response addFreeTime(String startTime, String endTime, String stuId,String userId);

    Response initStudentInfo(String userId);

    Response getMsg(String userId);

    Response delectMsg(String userId, String newsId);

    Response getStudentsInfo(Integer page);

    Response getStudentInfoById(String userId);

    Response changeStudentInfo(Student student);

    Response getStudentPage();

    Response getJobRecord(String userId);

    Response studentList(Integer page);

    Response studentListByJobType(String jobType, Integer page);
}
