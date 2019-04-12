package com.wenyizhou.job.model;

public class JobType {
    private int jobId;
    private String typeName;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "JobType{" +
                "jobId=" + jobId +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
