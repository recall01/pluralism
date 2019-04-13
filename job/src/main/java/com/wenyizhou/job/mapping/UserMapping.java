package com.wenyizhou.job.mapping;

import com.wenyizhou.job.model.User;
import com.wenyizhou.job.model.VO.TeacherVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapping {

    boolean insert(User user);

    User select(Map m);

    User selectUserById(String userId);

    boolean updateInfo(User user);

    TeacherVO selectTeacherById(String userId);
}
