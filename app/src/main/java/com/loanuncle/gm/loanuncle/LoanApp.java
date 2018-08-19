package com.loanuncle.gm.loanuncle;

import android.app.Application;

/**
 * Created by GM on 2018/8/18.
 * @description 自定义application类
 */

public class LoanApp extends Application{

    private static LoanApp instance;

    public static synchronized LoanApp getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
