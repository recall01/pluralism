package com.wenyizhou.job.model;

public class FreeTime {
    private int freId;
    private String startTime = "";
    private String endTime = "";
    private String stuId;

    public int getFreId() {
        return freId;
    }

    public void setFreId(int freId) {
        this.freId = freId;
    }

    public String getStartTime() {
        if(startTime.length()>20){
            return startTime.substring(0,19);
        }else {
            return startTime;
        }
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        if(endTime.length()>20){
            return endTime.substring(0,19);
        }else {
            return endTime;
        }
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
}
