package com.wenyizhou.job.model;

import java.util.ArrayList;
import java.util.List;

public class Job {
    private int jobId;
    private String jobName;
    private String introduction;
    private String jobType;
    private String location;
    private String salary;
    private String experience;
    private String userId;
    private String pubTime;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<String> getJobType() {
        if(jobType != null){
            String[] jobs = jobType.split(",");
            if(jobs.length == 0){
                return null;
            }
            List jobList = new ArrayList();
            for (int i = 0;i<jobs.length;i++){
                jobList.add(jobs[i]);
            }
            return jobList;
        }
        return null;
    }
//测试
    public String getStringJob() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPubTime() {
        if(pubTime != null){
            if(pubTime.length()>20){
                return pubTime.substring(0,19);
            }else {
                return pubTime;
            }
        }
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", jobName='" + jobName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", jobType='" + jobType + '\'' +
                ", location='" + location + '\'' +
                ", salary='" + salary + '\'' +
                ", experience='" + experience + '\'' +
                ", userId='" + userId + '\'' +
                ", pubTime='" + pubTime + '\'' +
                '}';
    }
}
