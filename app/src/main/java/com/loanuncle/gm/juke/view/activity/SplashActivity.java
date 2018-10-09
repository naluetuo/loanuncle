package com.loanuncle.gm.juke.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.loanuncle.gm.baselibrary.mvpbase.baseimpl.BaseActivity;
import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.constant.UserConstant;
import com.loanuncle.gm.juke.constant.VersionConstant;

import java.util.ArrayList;

/**
 * Created by GM on 2018/8/25.
 * @description 启动页
 */

public class SplashActivity extends BaseActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);

        checkPermission();
    }

    @Override
    public void initView() {

    }

    private void initContact(){
        /** 初始化版本信息 */
        VersionConstant.init(this);
        /** 初始化用户信息 */
        UserConstant.init(this);
    }

    /**
     * 延时跳转
     * */
    private void jumpDelay(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        },1000); // 延时1秒
    }

    /**
     * 跳转到主界面
     * */
    private void startMainActivity(){
        initContact();
//        if(TextUtils.isEmpty(UserConstant.ACCOUNT_ID)){
//            intent = new Intent(this,LoginActivity.class);
//            intent.putExtra("isfromSplash",true);
//        }else {
//            intent = new Intent(this,HomeActivity.class);
//        }
        intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 检查权限
     * */
    private void checkPermission() {
        if (Integer.valueOf(android.os.Build.VERSION.SDK) > 22) {

            ArrayList needPermission = new ArrayList();

            //手机状态读权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                needPermission.add(Manifest.permission.READ_PHONE_STATE);
            }

            //网络权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                needPermission.add(Manifest.permission.INTERNET);
            }

            //网络状态权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                needPermission.add(Manifest.permission.ACCESS_NETWORK_STATE);
            }

            //wifi状态权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                needPermission.add(Manifest.permission.ACCESS_WIFI_STATE);
            }

            if (needPermission.size() > 0) {
                String[] permission = new String[needPermission.size()];
                needPermission.toArray(permission);
                ActivityCompat.requestPermissions(this, permission, 1);
            } else {
                jumpDelay();
            }
        } else {
            //如果SDK小于6.0则不去动态申请权限
            jumpDelay();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] < 0) {
                    new AlertDialog.Builder(SplashActivity.this).setTitle("系统提示")
                            .setMessage("授权拒绝，请重新登入并授予权限！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    finish();
                                }
                            }).show();
                    return;
                }
            }
            jumpDelay();
        }
    }

}
