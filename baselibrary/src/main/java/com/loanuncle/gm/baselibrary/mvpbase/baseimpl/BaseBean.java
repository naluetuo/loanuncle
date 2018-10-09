package com.loanuncle.gm.baselibrary.mvpbase.baseimpl;


/**
 * Created by GM on 2018/8/20.
 * @description 基础实体类，仅用于判断是否请求成功
 */
public class BaseBean<T> {

    private String code;
    private String message;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
