package com.wenyizhou.job.model.VO;

import com.wenyizhou.job.model.AppJob;
import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.User;

import java.util.List;

public class AppJobVO extends AppJob {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
