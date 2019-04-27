package com.wenyizhou.job.model;

import com.wenyizhou.job.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class AppJob {
    protected Integer appId;
    protected Integer jobId;
    protected String userId;
    protected String appTime;
    //0:未知状态,1:未处理状态,2:已处理状态,3:同意,4:拒绝
    protected Integer status;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
