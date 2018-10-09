package com.loanuncle.gm.baselibrary.mvpbase;

/**
 * Created by GM on 2018/8/20.
 * @description 视图基础公共接口
 */

public interface BaseView {

    /**
     * 显示加载对话框
     * */
    void showLoadingDialog(String msg);

    /**
     * 消除加载对话框
     * */
    void dismissLoadingDialog();
}
