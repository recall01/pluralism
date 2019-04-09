package com.wenyizhou.job.utils;

public enum ErrorCode {
    //1000开头的与参数有关
    PARAMETER_ERROR(10001,"参数不合法"),
    //2000开头的与账号有关
    ACCOUNT_PASSWORD_ERROR(20001,"账号或密码错误"),
    ACCOUNT_NOT_EXIST(20002,"账号不存在"),
    //3000开头的与数据库查询数据有关
    DATA_NOT_EXIST(30001,"数据不存在"),
    SQL_OPERATING_FAIL(30002,"SQL执行失败"),
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
