package com.loanuncle.gm.juke.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.baselibrary.mvpbase.baseimpl.BaseFragment;
import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.bean.request.GetNewUserRequestBean;
import com.loanuncle.gm.juke.bean.request.LogoutRequestBean;
import com.loanuncle.gm.juke.bean.response.FeedBackResponseBean;
import com.loanuncle.gm.juke.bean.response.GetNewUserResponseBean;
import com.loanuncle.gm.juke.bean.response.LogoutResponseBean;
import com.loanuncle.gm.juke.constant.ResponseCodeConstant;
import com.loanuncle.gm.juke.constant.UserConstant;
import com.loanuncle.gm.juke.contact.GetNewUserContact;
import com.loanuncle.gm.juke.contact.PersonCenterContact;
import com.loanuncle.gm.juke.presenter.GetNewUserPresenter;
import com.loanuncle.gm.juke.presenter.PersonCenterPresenter;
import com.loanuncle.gm.juke.util.SharePreferenceUtils;
import com.loanuncle.gm.juke.util.ToastUtils;
import com.loanuncle.gm.juke.view.activity.LoginActivity;


/**
 * Created by GM on 2018/8/24.
 * @description 该项目的基本Fragment
 */

public abstract class LoanBaseFragment<P extends BasePresenter> extends BaseFragment implements
        PersonCenterContact.view, GetNewUserContact.view {

    protected P presenter;
    public Toolbar toolbar;
    public TextView mToolBarTitle;
    public ImageView mToolBarRightImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detach();
        }
    }

    /**
     * 初始化toolbar
     * */
    public void initToolBar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.login_toolbar);
        toolbar.setTitle(" ");

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        mToolBarTitle = view.findViewById(R.id.login_title);
        mToolBarRightImage = view.findViewById(R.id.right_menu);
    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

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
        GetNewUserPresenter getNewUserPresenter = new GetNewUserPresenter(this);
        GetNewUserRequestBean getNewUserRequestBean = new GetNewUserRequestBean();
        getNewUserRequestBean.setToken(UserConstant.TOKEN);
        getNewUserRequestBean.setMobile(UserConstant.MOBILE);
        getNewUserPresenter.getNewUserInfoRequest(getNewUserRequestBean);

    }

    public abstract void initView(View view);

    public abstract P initPresenter();

    @Override
    public void logoutResponse(LogoutResponseBean logoutResponseBean) {
        String code = logoutResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            UserConstant.LOGIN_STATUS = false;
            UserConstant.ACCOUNT_ID = "";
            SharePreferenceUtils.saveObject(getActivity(),SharePreferenceUtils.ACCOUNT_ID,UserConstant.ACCOUNT_ID);
        }
    }

    @Override
    public void getNewUserInfoResponse(GetNewUserResponseBean getNewUserResponseBean) {
        String code = getNewUserResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            UserConstant.ACCOUNT_ID = getNewUserResponseBean.getResult();
            SharePreferenceUtils.saveObject(getActivity(),SharePreferenceUtils.ACCOUNT_ID,UserConstant.ACCOUNT_ID);
        }else if(ResponseCodeConstant.CODE_401.equals(code)){
            ToastUtils.showShort(getActivity(),getNewUserResponseBean.getErrorMsg());
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void feedBackResponse(FeedBackResponseBean feedBackResponseBean) {

    }
}
