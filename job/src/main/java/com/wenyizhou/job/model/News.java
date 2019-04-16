package com.wenyizhou.job.model;

import com.wenyizhou.job.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class News {
    private int newsId;
    private int jobId;
    private String sendId;
    private String accId;
    //1.同意申请,2.拒绝申请
    private int newsType;
    private String newsInfo;
    private String addTime;

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public String getNewsInfo() {
        return newsInfo;
    }

    public void setNewsInfo(String newsInfo) {
        this.newsInfo = newsInfo;
    }

    public String getAddTime() {
        return TimeUtil.handTimeForm(addTime);
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId=" + newsId +
                ", jobId=" + jobId +
                ", sendId='" + sendId + '\'' +
                ", accId='" + accId + '\'' +
                ", newsType=" + newsType +
                ", newsInfo='" + newsInfo + '\'' +
                ", addTime='" + addTime + '\'' +
                '}';
    }
}
