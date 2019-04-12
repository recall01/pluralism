package com.wenyizhou.job.model.VO;

import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.User;

public class JobVO extends Job {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
