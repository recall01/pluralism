package com.wenyizhou.job.model.VO;

import com.wenyizhou.job.model.FreeTime;
import com.wenyizhou.job.model.Student;

import java.util.List;

public class StudentVO extends Student {
    private List<FreeTime> freeTimes;

    public List<FreeTime> getFreeTimes() {
        return freeTimes;
    }

    public void setFreeTimes(List<FreeTime> freeTimes) {
        this.freeTimes = freeTimes;
    }

}
