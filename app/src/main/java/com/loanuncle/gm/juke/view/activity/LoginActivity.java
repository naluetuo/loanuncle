package com.loanuncle.gm.juke.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.base.LoanBaseActivity;
import com.loanuncle.gm.juke.bean.request.AgreementRequestBean;
import com.loanuncle.gm.juke.bean.request.LoginRequestBean;
import com.loanuncle.gm.juke.bean.request.VerCodeRequestBean;
import com.loanuncle.gm.juke.bean.response.AgreementResponseBean;
import com.loanuncle.gm.juke.bean.response.LoginResponseBean;
import com.loanuncle.gm.juke.bean.response.VerCodeResponseBean;
import com.loanuncle.gm.juke.constant.OtherConstant;
import com.loanuncle.gm.juke.constant.ResponseCodeConstant;
import com.loanuncle.gm.juke.constant.UserConstant;
import com.loanuncle.gm.juke.contact.LoginContact;
import com.loanuncle.gm.juke.presenter.LoginPresenter;
import com.loanuncle.gm.juke.util.PhoneFormatCheckUtils;
import com.loanuncle.gm.juke.util.SharePreferenceUtils;
import com.loanuncle.gm.juke.util.ToastUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by GM on 2018/8/16.
 * @description 登录界面
 */

public class LoginActivity extends LoanBaseActivity<LoginContact.presenter> implements View.OnClickListener, LoginContact.view {

    private TextView mLoginBtn;
    private TextView mGetVerCodeBtn;
    private EditText mPhoneNumberEdt;
    private EditText mVerCodeEdt;
    private TextView mCountDownTxt;
    private RelativeLayout mCountDownLayout;
    private TextView mAgreementTxt;
    private Button mAgreementBox;

    /** 电话号码 */
    private String mPhoneNumber;
    /** 验证码 */
    private String mVerCode;
    private boolean checked = true;
    /** 是否从闪屏页跳转过来 */
    private boolean isFromSplash;

    private Disposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        isFromSplash = getIntent().getBooleanExtra("isfromSplash",false);
        initToolBar();
    }

    @Override
    public LoginContact.presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void initView() {
        mLoginBtn = findViewById(R.id.login_btn);
        mGetVerCodeBtn = findViewById(R.id.getvercode_btn);
        mPhoneNumberEdt = findViewById(R.id.phonenumber_edt);
        mVerCodeEdt = findViewById(R.id.vercode_edt);
        mCountDownTxt = findViewById(R.id.count_down);
        mCountDownLayout = findViewById(R.id.count_down_layout);
        mAgreementTxt = findViewById(R.id.agreement_txt);
        mAgreementBox = findViewById(R.id.agreement_box);

        mLoginBtn.setOnClickListener(this);
        mGetVerCodeBtn.setOnClickListener(this);
        mAgreementTxt.setOnClickListener(this);
        mAgreementBox.setOnClickListener(this);

        String lastAccount = SharePreferenceUtils.readString(this,SharePreferenceUtils.PHONE_NUMBER);
        if(!TextUtils.isEmpty(lastAccount)){
            mPhoneNumberEdt.setText(lastAccount);
        }
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        mToolBarRightImage.setVisibility(View.GONE);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFromSplash){
                    jumpToHome();
                }else {
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                mVerCode = mVerCodeEdt.getText().toString();
                mPhoneNumber = mPhoneNumberEdt.getText().toString();
                if(checkPhoneNumber(mPhoneNumber) && checkVerCode(mVerCode) && checkAgreement()){
                    requestLogin();
                }
                break;
            case R.id.getvercode_btn:
                mPhoneNumber = mPhoneNumberEdt.getText().toString();
                if(checkPhoneNumber(mPhoneNumber)){
                    requestVerCode();
                }
                break;
            case R.id.agreement_txt:
                requestAgreement();
                break;
            case R.id.agreement_box:
                if(checked){
                    mAgreementBox.setBackgroundResource(R.mipmap.box_uncheck);
                    checked = false;
                }else {
                    mAgreementBox.setBackgroundResource(R.mipmap.box_checked);
                    checked = true;
                }
                break;
                default:
        }
    }

    /**
     * 检查手机格式是否正确
     * */
    public boolean checkPhoneNumber(String phoneNumber){
        if(TextUtils.isEmpty(phoneNumber)){
            ToastUtils.showShort(this,getResources().getString(R.string.phonenumber_empty));
            return false;
        }else if(PhoneFormatCheckUtils.isPhoneLegal(phoneNumber)){
            return true;
        }else {
            ToastUtils.showShort(this,getResources().getString(R.string.phonenumber_wrong));
            return false;
        }
    }

    /**
     * 检查验证码格式是否正确
     * */
    public boolean checkVerCode(String verCode){
        if(TextUtils.isEmpty(verCode)){
            ToastUtils.showShort(this,getResources().getString(R.string.vercode_empty));
            return false;
        } else if(verCode.length() != OtherConstant.VERCODE_LENGTH){
            ToastUtils.showShort(this,getResources().getString(R.string.vercode_wrong));
            return false;
        }else {
            return true;
        }
    }

    /**
     * 检查是否勾选协议
     * */
    public boolean checkAgreement(){
        if(!checked){
            ToastUtils.showShort(this,getResources().getString(R.string.agreement_check));
        }
        return checked;
    }

    /**
     * 请求获取验证码
     * */
    private void requestVerCode(){
        VerCodeRequestBean requestBean = new VerCodeRequestBean();
        requestBean.setMobile(mPhoneNumber);
        presenter.requestVerCode(requestBean);
    }

    /**
     * 登录请求
     * */
    private void requestLogin(){
        LoginRequestBean loginRequestBean = new LoginRequestBean();
        loginRequestBean.setMobile(mPhoneNumber);
        loginRequestBean.setVerifyCode(mVerCode);
        presenter.requestLogin(loginRequestBean);
    }

    /**
     * 协议请求
     * */
    private void requestAgreement(){
        AgreementRequestBean agreementRequestBean = new AgreementRequestBean();
        presenter.requestAgreement(agreementRequestBean);
    }

    /**
     * 开始倒计时
     * */
    private void startCountDown(){
        mCountDownLayout.setVisibility(View.VISIBLE);
        mGetVerCodeBtn.setVisibility(View.GONE);
        // 倒计时 60s
        mDisposable = Flowable.intervalRange(0, 61, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mCountDownTxt.setText(String.valueOf(60 - aLong));
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        mCountDownLayout.setVisibility(View.GONE);
                        mGetVerCodeBtn.setVisibility(View.VISIBLE);
                    }
                })
                .subscribe();
    }

    /**
     * 记录上次登录的账号
     * */
    private void saveLastAcount(){
        SharePreferenceUtils.saveObject(this,SharePreferenceUtils.PHONE_NUMBER,mPhoneNumber);
        UserConstant.MOBILE = mPhoneNumber;
    }

    /**
     * 跳转到主界面
     * */
    private void jumpToHome(){
        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 跳转到协议网页
     * */
    private void jumptoAgreement(String url){
//        Intent intent = new Intent(Intent.ACTION_VIEW); //Intent.ACTION_VIEW固定写法
//        intent.setData(Uri.parse(url)); //url是你要跳转的网页地址
//        startActivity(intent);
        Intent intent = new Intent(this,AgreementWebActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);

    }

    @Override
    public void verCodeResponse(VerCodeResponseBean verCodeResponseBean) {
        String code = verCodeResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            startCountDown();
            Toast.makeText(this,"验证码已发送",Toast.LENGTH_SHORT).show();
        }
        else if(ResponseCodeConstant.CODE_401.equals(code)){
            overallLogout();
        }else if(ResponseCodeConstant.CODE_402.equals(code)){
            overallGetNewUserInfo();
        }else {
            Toast.makeText(this,verCodeResponseBean.getErrorMsg(),Toast.LENGTH_SHORT).show();
            mGetVerCodeBtn.setClickable(false);
        }
    }

    @Override
    public void loginResponse(LoginResponseBean loginResponseBean) {
        String code = loginResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            UserConstant.ACCOUNT_ID = loginResponseBean.getResult().getAccountId();
            UserConstant.TOKEN = loginResponseBean.getResult().getToken();
            UserConstant.LOGIN_STATUS = true;
            SharePreferenceUtils.saveObject(LoginActivity.this,SharePreferenceUtils.ACCOUNT_ID,UserConstant.ACCOUNT_ID);
            SharePreferenceUtils.saveObject(LoginActivity.this,SharePreferenceUtils.TOKEN,UserConstant.TOKEN);
            saveLastAcount();
            jumpToHome();
            finish();
        }else if(ResponseCodeConstant.CODE_401.equals(code)){
            overallLogout();
        }else if(ResponseCodeConstant.CODE_402.equals(code)){
            overallGetNewUserInfo();
        }
    }

    @Override
    public void agreementResponse(AgreementResponseBean agreementResponseBean) {
        String code = agreementResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            String url = agreementResponseBean.getResult();
            if(!TextUtils.isEmpty(url)){
                jumptoAgreement(url);
            }
        }else if(ResponseCodeConstant.CODE_401.equals(code)){
            overallLogout();
        }else if(ResponseCodeConstant.CODE_402.equals(code)){
            overallGetNewUserInfo();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
