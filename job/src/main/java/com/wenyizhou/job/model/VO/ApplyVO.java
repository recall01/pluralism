package com.wenyizhou.job.model.VO;

import com.wenyizhou.job.model.Apply;
import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.User;

public class ApplyVO extends Apply {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
