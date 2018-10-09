package com.loanuncle.gm.baselibrary.mvpbase.baseimpl;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.loanuncle.gm.baselibrary.mvpbase.BaseView;
import com.loanuncle.gm.baselibrary.utils.ActivityManager;
import com.umeng.analytics.MobclickAgent;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by GM on 2018/8/20.
 * @description 基础Activity
 */
public abstract class BaseActivity extends SupportActivity
        implements BaseView {


    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        ActivityManager.getAppInstance().addActivity(this);//将当前activity添加进入管理栈
        initView();
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getAppInstance().removeActivity(this);//将当前activity移除管理栈
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    private void hideKeyboard(View view){
        InputMethodManager im = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null){
            im.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    /**
     * 初始化视图
     * */
    public abstract void initView();
}
