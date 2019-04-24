package com.wenyizhou.job.model;

import com.wenyizhou.job.utils.TimeUtil;

public class Apply {
    protected int appId;
    //0为申请成为老师/家长
    protected int appType;
    protected String appTime;
    protected String userId;

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public String getAppTime() {
        return TimeUtil.handTimeForm(appTime);
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Apply{" +
                "appId=" + appId +
                ", appType=" + appType +
                ", appTime='" + appTime + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
