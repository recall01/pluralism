package com.wenyizhou.job.model;

import com.wenyizhou.job.utils.ErrorCode;

public class Response<T> {
    private int status;
    private T data;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    //当传递是异常信息时，包装数据
    public void setError(ErrorCode e){
        this.status = e.getErrCode();
        this.msg = e.getErrMsg();
    }
    //当传递是异常信息时，包装数据
    public void setError(int errorCode,String errMsg){
        this.status = errorCode;
        this.msg = errMsg;
    }
}
