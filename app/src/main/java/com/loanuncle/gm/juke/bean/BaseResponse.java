package com.loanuncle.gm.juke.bean;

/**
 * Created by GM on 2018/8/20.
 * @description 基本请求返回类
 */

public class BaseResponse {
    /**
     * errorCode : string
     * errorMsg : string
     * errorStack : string
     * result : false
     * returnCode : string
     * success : false
     * timeOut : true
     */

    private String errorCode;
    private String errorMsg;
    private String errorStack;
    private String returnCode;
    private boolean success;
    private boolean timeOut;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorStack() {
        return errorStack;
    }

    public void setErrorStack(String errorStack) {
        this.errorStack = errorStack;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isTimeOut() {
        return timeOut;
    }

    public void setTimeOut(boolean timeOut) {
        this.timeOut = timeOut;
    }
}
