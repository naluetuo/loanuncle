package com.loanuncle.gm.juke.view.fragment;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.base.LoanBaseFragment;
import com.loanuncle.gm.juke.bean.request.LogoutRequestBean;
import com.loanuncle.gm.juke.bean.response.LogoutResponseBean;
import com.loanuncle.gm.juke.constant.OtherConstant;
import com.loanuncle.gm.juke.constant.ResponseCodeConstant;
import com.loanuncle.gm.juke.constant.UserConstant;
import com.loanuncle.gm.juke.contact.PersonCenterContact;
import com.loanuncle.gm.juke.presenter.PersonCenterPresenter;
import com.loanuncle.gm.juke.util.SharePreferenceUtils;
import com.loanuncle.gm.juke.util.ToastUtils;
import com.loanuncle.gm.juke.view.CommonDialog;
import com.loanuncle.gm.juke.view.activity.BillCompletedActivity;
import com.loanuncle.gm.juke.view.activity.LoginActivity;

/**
 * Created by GM on 2018/8/18.
 * @description 个人中心页面
 */

public class PersonCenterFragment extends LoanBaseFragment<PersonCenterContact.presenter> implements
        CommonDialog.ICommonDialogClickCallBack, View.OnClickListener, PersonCenterContact.view{

    private Context mContext;
    private TextView mLoginCancleBtn;
    private TextView mLoginTxt;
    private RelativeLayout mPublicLayout;
    private CommonDialog mCommonDialog;
    private CommonDialog mPublicDialog;

    private RelativeLayout mBillLayout;

    public static PersonCenterFragment newInstance(Context context){
        PersonCenterFragment fragment = new PersonCenterFragment();
        fragment.mContext = context;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personcenter,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolBar(view);
    }

    @Override
    public void initView(View view) {
        mLoginCancleBtn = view.findViewById(R.id.login_cancle);
        mBillLayout = view.findViewById(R.id.bill_layout);
        mLoginTxt = view.findViewById(R.id.login_txt);
        mPublicLayout = view.findViewById(R.id.public_layout);
        mLoginCancleBtn.setOnClickListener(this);
        mBillLayout.setOnClickListener(this);
        mLoginTxt.setOnClickListener(this);
        mPublicLayout.setOnClickListener(this);

        updateView(UserConstant.LOGIN_STATUS);
    }

    @Override
    public PersonCenterContact.presenter initPresenter() {
        return new PersonCenterPresenter(this);
    }

    @Override
    public void initToolBar(View view) {
        super.initToolBar(view);
        mToolBarTitle.setText(mContext.getResources().getString(R.string.person_center));
        mToolBarRightImage.setVisibility(View.GONE);
    }

    /**
     * 弹出对话框
     * */
    public void showDialog(){
        mCommonDialog = new CommonDialog(getContext(),mContext.getResources().getString(R.string.is_goback),this);
        mCommonDialog.setCanceledOnTouchOutside(true);
        mCommonDialog.setCancelable(true);
        mCommonDialog.show();
    }

    /**
     * 弹出公众号对话框
     * */
    public void showPublicDialog(){
        mPublicDialog = new CommonDialog(getContext(),mContext.getResources().getString(R.string.public_dialog),this);
        mPublicDialog.setCanceledOnTouchOutside(true);
        mPublicDialog.setCancelable(true);
        mPublicDialog.show();
    }

    /**
     * 登录状态和未登录状态界面切换
     * */
    private void updateView(boolean login){
        if(login){
            mLoginCancleBtn.setVisibility(View.VISIBLE);
            mLoginTxt.setText(UserConstant.MOBILE);
        }else {
            mLoginCancleBtn.setVisibility(View.GONE);
            mLoginTxt.setText(mContext.getResources().getString(R.string.login_immediately));
        }
    }

    /**
     * 跳转到微信
     */
    private void getWechatApi(){
        ClipboardManager tvCopy = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        tvCopy.setText("XXX");
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName(OtherConstant.WEICHART_PACKAGE,OtherConstant.WEICHART_LANUCH);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // TODO: handle exception
            ToastUtils.showShort(mContext,mContext.getResources().getString(R.string.uninstall_weixin));
        }
    }

    @Override
    public void dialogDeleteCallBack() {
        if(mCommonDialog != null){
            mCommonDialog.dismiss();
            mCommonDialog = null;
        }
        if(mPublicDialog != null){
            mPublicDialog.dismiss();
            mPublicDialog = null;
        }

    }

    @Override
    public void dialogSureCallBack() {
        if(mCommonDialog != null){
            mCommonDialog.dismiss();
            mCommonDialog = null;
            LogoutRequestBean logoutRequestBean = new LogoutRequestBean();
            presenter.logoutRequest(logoutRequestBean);
        }
        if(mPublicDialog != null){
            mPublicDialog.dismiss();
            mPublicDialog = null;
            getWechatApi();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_cancle:
                showDialog();
                break;
            case R.id.bill_layout:
                if(UserConstant.LOGIN_STATUS){
                    Intent intent = new Intent(mContext, BillCompletedActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent1 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.login_txt:
                Intent intent1 = new Intent(mContext, LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.public_layout:
                showPublicDialog();
                mPublicDialog.setBottomText("朕考虑一下","朕去看看");
                break;
                default:
        }
    }

    @Override
    public void logoutResponse(LogoutResponseBean logoutResponseBean) {
        String code = logoutResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            ToastUtils.showShort(mContext,"退出登录");
            UserConstant.LOGIN_STATUS = false;
            UserConstant.ACCOUNT_ID = "";
            SharePreferenceUtils.saveObject(mContext,SharePreferenceUtils.ACCOUNT_ID,UserConstant.ACCOUNT_ID);
            updateView(false);
        }else if(ResponseCodeConstant.CODE_401.equals(code)){
            overallLogout();
        }else if(ResponseCodeConstant.CODE_402.equals(code)){
            overallGetNewUserInfo();
        }
    }

}
