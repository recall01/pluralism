package com.wenyizhou.job.model;

import com.wenyizhou.job.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class AppJob {
    protected int appId;
    protected int jobId;
    protected String userId;
    protected String appTime;
    protected int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppTime() {
        return TimeUtil.handTimeForm(appTime);
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    @Override
    public String toString() {
        return "AppJob{" +
                "appId=" + appId +
                ", jobId=" + jobId +
                ", userId='" + userId + '\'' +
                ", appTime='" + appTime + '\'' +
                '}';
    }
}
