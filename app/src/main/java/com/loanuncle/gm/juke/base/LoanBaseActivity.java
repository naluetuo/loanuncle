package com.loanuncle.gm.juke.base;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.baselibrary.mvpbase.baseimpl.BaseActivity;
import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.bean.request.GetNewUserRequestBean;
import com.loanuncle.gm.juke.bean.request.LogoutRequestBean;
import com.loanuncle.gm.juke.bean.response.FeedBackResponseBean;
import com.loanuncle.gm.juke.bean.response.GetNewUserResponseBean;
import com.loanuncle.gm.juke.bean.response.LogoutResponseBean;
import com.loanuncle.gm.juke.constant.OtherConstant;
import com.loanuncle.gm.juke.constant.ResponseCodeConstant;
import com.loanuncle.gm.juke.constant.UserConstant;
import com.loanuncle.gm.juke.contact.GetNewUserContact;
import com.loanuncle.gm.juke.contact.PersonCenterContact;
import com.loanuncle.gm.juke.eventbus.NewAccountEventBus;
import com.loanuncle.gm.juke.eventbus.StatusEventBus;
import com.loanuncle.gm.juke.presenter.GetNewUserPresenter;
import com.loanuncle.gm.juke.presenter.PersonCenterPresenter;
import com.loanuncle.gm.juke.util.SharePreferenceUtils;
import com.loanuncle.gm.juke.view.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by GM on 2018/8/20.
 * @description 该项目Activity的基类
 */

public abstract class LoanBaseActivity<P extends BasePresenter> extends BaseActivity implements
        PersonCenterContact.view, GetNewUserContact.view {

    public Toolbar toolbar;
    public TextView mToolBarTitle;
    public ImageView mToolBarRightImage;
    public TextView mWebClosebtn;
    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(OtherConstant.onPoseStatus){
            OtherConstant.isFirstLoad = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        boolean isback = isApplicationBroughtToBackground(this);
        OtherConstant.onPoseStatus = isback;
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detach();//在presenter中解绑释放view
            presenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void initView() {

    }

    /**
     * 在子类中初始化对应的presenter
     *
     * @return 相应的presenter
     */
    public abstract P initPresenter();

    /**
     * 初始化toolbar
     * */
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        toolbar.setTitle(" ");

        setSupportActionBar(toolbar);

        mToolBarTitle = findViewById(R.id.login_title);
        mToolBarRightImage = findViewById(R.id.right_menu);
        mWebClosebtn = findViewById(R.id.web_closebtn);
    }

    /**
     * 设置左边按钮
     * */
    public void setNavicationIcon(){
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
    }

    /**
     *设置标题
     * */
    public void setTitle(String title){
        if(mToolBarTitle != null && title != null){
            mToolBarTitle.setText(title);
        }
    }

    /**
     * 设置toolbar右侧按钮是否显示
     * */
    public void setRightImageVisiable(boolean visiable){
        if(visiable){
            mToolBarRightImage.setVisibility(View.VISIBLE);
        }else {
            mToolBarRightImage.setVisibility(View.GONE);
        }
    }

    /**
     * 跳转到登录界面
     * */
    public void jumptoLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 全局退出登录请求
     * */
    public void overallLogout(){
        PersonCenterPresenter personCenterPresenter = new PersonCenterPresenter(this);
        LogoutRequestBean logoutRequestBean = new LogoutRequestBean();
        personCenterPresenter.logoutRequest(logoutRequestBean);
    }

    /**
     * 全局验证用户信息
     * */
    public void overallGetNewUserInfo(){
        GetNewUserPresenter  getNewUserPresenter = new GetNewUserPresenter(this);
        GetNewUserRequestBean getNewUserRequestBean = new GetNewUserRequestBean();
        getNewUserRequestBean.setToken(UserConstant.TOKEN);
        getNewUserRequestBean.setMobile(UserConstant.MOBILE);
        getNewUserPresenter.getNewUserInfoRequest(getNewUserRequestBean);
    }

    /**
     * 判断应用是否在后台
     * */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;

    }

    @Override
    public void logoutResponse(LogoutResponseBean logoutResponseBean) {
        String code = logoutResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            UserConstant.LOGIN_STATUS = false;
            UserConstant.ACCOUNT_ID = "";
            SharePreferenceUtils.saveObject(LoanBaseActivity.this,SharePreferenceUtils.ACCOUNT_ID,UserConstant.ACCOUNT_ID);
        }
//        jumptoLogin();
        finish();
        EventBus.getDefault().post(new StatusEventBus());
    }

    @Override
    public void feedBackResponse(FeedBackResponseBean feedBackResponseBean) {

    }

    @Override
    public void getNewUserInfoResponse(GetNewUserResponseBean getNewUserResponseBean) {
        String code = getNewUserResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            UserConstant.ACCOUNT_ID = getNewUserResponseBean.getResult();
            SharePreferenceUtils.saveObject(LoanBaseActivity.this,SharePreferenceUtils.ACCOUNT_ID,UserConstant.ACCOUNT_ID);
        }
//        Toast.makeText(this, "getNewAccountInfo调用返回成功", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new NewAccountEventBus());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
