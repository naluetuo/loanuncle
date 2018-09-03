package com.loanuncle.gm.juke.constant;

import android.content.Context;

import com.loanuncle.gm.juke.util.SystemUtil;
import com.loanuncle.gm.juke.util.VersionUtil;

/**
 * Created by GM on 2018/8/22.
 * @description 版本信息相关常量
 */

public class VersionConstant {

    /** 版本号 */
    public static int VERSION_CODE;
    /** 版本名称 */
    public static String VERSION_NAME;
    /** 包名 */
    public static String PACKAGE_NAME;
    /** 系统类别 */
    public static String OS_TYPE;
    /** 安卓系统版本 */
    public static String ANDROID_OSCODE;
    /** 手机厂商 */
    public static String PHONE_TYPE;
    /** 渠道 */
    public static String CHANNEL;
    /** 硬件地址 */
    public static String IMMEI;
    /** 版本升级地址 */
    public static String UPDATE_URL;
    /** 是否强制更新 */
    public static boolean ISFORCE_UPDATE;

    /**
     * 初始化
     * */
    public static void init(Context context){
        VERSION_CODE = VersionUtil.getLocalVersion(context);
        VERSION_NAME = VersionUtil.getLocalVersionName(context);
        ANDROID_OSCODE = SystemUtil.getSystemVersion();
        PHONE_TYPE = SystemUtil.getDeviceBrand();
        IMMEI = SystemUtil.getIMEI(context);
        PACKAGE_NAME = "juke";
        OS_TYPE = "Android";
        CHANNEL = "guanwang";
    }
}
