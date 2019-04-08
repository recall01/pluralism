package com.wenyizhou.job.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IDUtil {
    public static String getUserId(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        String time = simpleDateFormat.format(date);
        Random random = new Random();
        int i = random.nextInt(9999);
        i = i + 1000;
        return time+ String.valueOf(i);
    }
}
