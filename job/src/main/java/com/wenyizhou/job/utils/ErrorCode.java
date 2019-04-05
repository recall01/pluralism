package com.wenyizhou.job.utils;

public enum ErrorCode {
    //1000开头的与参数有关
    PARAMETER_ERROR(10001,"参数不合法"),
    //9999开头的与系统有关
    SYSTEM_EXCEPTION(99999,"系统异常");
    private int errCode;
    private String errMsg;

    private ErrorCode(int errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
    public int getErrCode(){
        return errCode;
    }
    public String getErrMsg(){
        return errMsg;
    }
}
