package com.wenyizhou.job.model.VO;

import com.wenyizhou.job.model.Job;
import com.wenyizhou.job.model.User;

import java.util.List;

public class TeacherVO extends User {
    //一个老师可以发布多个工作
    private List<Job> jobs;

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
