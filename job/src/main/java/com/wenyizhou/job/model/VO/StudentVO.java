package com.wenyizhou.job.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wenyizhou.job.model.FreeTime;
import com.wenyizhou.job.model.Student;

import java.util.List;

@JsonIgnoreProperties
public class StudentVO extends Student {
    private List<FreeTime> freeTimes;

    public List<FreeTime> getFreeTimes() {
        //如果空闲时间段大于5就进行截取
/*        if(freeTimes.size()>5){
            freeTimes = freeTimes.subList(0,4);
        }*/
        return freeTimes;
    }

    public void setFreeTimes(List<FreeTime> freeTimes) {
        this.freeTimes = freeTimes;
    }

}
