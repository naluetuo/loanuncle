package com.loanuncle.gm.juke.constant;

import android.content.Context;
import android.text.TextUtils;

import com.loanuncle.gm.juke.util.SharePreferenceUtils;

/**
 * Created by GM on 2018/8/22.
 * @description 用户信息常量
 */

public class UserConstant {

    /** 用户ID */
    public static String ACCOUNT_ID;
    /** 手机号码 */
    public static String MOBILE;
    /** 用户名 */
    public static String USER_NAME;
    /** Token值 */
    public static String TOKEN;
    /** 登录状态 */
    public static boolean LOGIN_STATUS;

    public static void init(Context context){
        ACCOUNT_ID = SharePreferenceUtils.readString(context,SharePreferenceUtils.ACCOUNT_ID);
        TOKEN = SharePreferenceUtils.readString(context,SharePreferenceUtils.TOKEN);
        MOBILE = SharePreferenceUtils.readString(context,SharePreferenceUtils.PHONE_NUMBER);
        if(TextUtils.isEmpty(ACCOUNT_ID)){
            LOGIN_STATUS = false;
        }else {
            LOGIN_STATUS = true;
        }
    }
}
