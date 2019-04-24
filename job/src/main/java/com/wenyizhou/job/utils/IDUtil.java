package com.wenyizhou.job.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IDUtil {
    public static String getUserId(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        String time = simpleDateFormat.format(date);
        String s = time.substring(0,1);
        if("0".equals(s)){
            time = "s"+time.substring(1);
        }
        Random random = new Random();
        int i = random.nextInt(9999);
        i = i + 1000;
        return "s"+time+ String.valueOf(i);
    }
}
