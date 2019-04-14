package com.wenyizhou.job.model.VO;

import com.wenyizhou.job.model.AppJob;
import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.User;

import java.util.List;

public class AppJobVO extends AppJob {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
