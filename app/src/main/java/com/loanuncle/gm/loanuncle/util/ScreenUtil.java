package com.loanuncle.gm.loanuncle.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.loanuncle.gm.loanuncle.LoanApp;

/**
 * Created by GM on 2018/8/18.
 * @description 屏幕工具类
 */

public class ScreenUtil {

    //获取屏幕的宽
    public static int getScreenWidth() {
        final Resources resources = LoanApp.getInstance().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    //获取屏幕的高
    public static int getScreenHeight() {
        final Resources resources = LoanApp.getInstance().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
