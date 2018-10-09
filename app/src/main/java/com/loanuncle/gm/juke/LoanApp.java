package com.loanuncle.gm.juke;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;

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
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,"");
    }
}
