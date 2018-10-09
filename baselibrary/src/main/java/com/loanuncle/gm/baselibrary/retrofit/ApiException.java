package com.loanuncle.gm.baselibrary.retrofit;

/**
 * Created by GM on 2018/8/20.
 * @description 自定义exception类
 */
public class ApiException extends RuntimeException {

    private int code;


    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException(String message) {
        super(new Throwable(message));

    }
}