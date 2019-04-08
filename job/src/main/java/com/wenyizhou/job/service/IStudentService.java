package com.wenyizhou.job.service;

import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.Student;

public interface IStudentService {

    Response addIntroduction(String introduction,String userId);

    Response addJobType(String jobType, String userId);

    Response changeInfo(Student student);

    Response delectFreeTime(String freId,String userId);
}
