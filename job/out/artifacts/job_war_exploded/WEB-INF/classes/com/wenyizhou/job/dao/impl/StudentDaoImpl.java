package com.wenyizhou.job.dao.impl;

import com.wenyizhou.job.dao.IStudentDao;
import com.wenyizhou.job.dao.IUserDao;
import com.wenyizhou.job.mapping.StudentMapping;
import com.wenyizhou.job.mapping.UserMapping;
import com.wenyizhou.job.model.Student;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.StudentVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class StudentDaoImpl implements IStudentDao {
    @Resource
    UserMapping userMapping;
    @Resource
    StudentMapping studentMapping;

    @Override
    public boolean addIntroduction(String introduction,String userId) {
        Map m = new HashMap();
        m.put("introduction",introduction);
        m.put("userId",userId);
        try {
            studentMapping.updateIntroduction(m);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addJobType(String jobType, String userId) {
        Map m = new HashMap();
        m.put("jobType",jobType);
        m.put("userId",userId);
        return studentMapping.updateJobType(m);
    }

    @Override
    public boolean changeInfo(Student student) {
        try {
            studentMapping.updateInfo(student);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delectFreeTime(String freId) {
        try {
            studentMapping.delectFreeTime(freId);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addFreeTime(String startTime, String endTime, String stuId) {
        try {
            Map m = new HashMap();
            m.put("startTime",startTime);
            m.put("endTime",endTime);
            m.put("stuId",stuId);
            studentMapping.addFreeTime(m);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean initStudentInfo(Student stu) {
        try {
            studentMapping.insertStudentInfo(stu);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
