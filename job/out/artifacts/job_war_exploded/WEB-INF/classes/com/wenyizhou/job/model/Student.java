package com.wenyizhou.job.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    protected String stuId;
    protected String introduction;
    protected String specialty;
    protected String salary;
    protected String jobType;
    protected String userId;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public List<String> getJobList(){
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId='" + stuId + '\'' +
                ", introduction='" + introduction + '\'' +
                ", specialty='" + specialty + '\'' +
                ", salary='" + salary + '\'' +
                ", jobType='" + jobType + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
