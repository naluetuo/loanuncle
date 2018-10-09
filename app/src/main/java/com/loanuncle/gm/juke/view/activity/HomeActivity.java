package com.loanuncle.gm.juke.view.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.base.LoanBaseActivity;
import com.loanuncle.gm.juke.bean.request.BaseInfoRequestBean;
import com.loanuncle.gm.juke.bean.response.BaseInfoResponseBean;
import com.loanuncle.gm.juke.constant.OtherConstant;
import com.loanuncle.gm.juke.constant.ResponseCodeConstant;
import com.loanuncle.gm.juke.constant.UserConstant;
import com.loanuncle.gm.juke.constant.VersionConstant;
import com.loanuncle.gm.juke.constant.WebConstance;
import com.loanuncle.gm.juke.contact.SplashContact;
import com.loanuncle.gm.juke.eventbus.ChangeButtonEventBus;
import com.loanuncle.gm.juke.presenter.SplashPresenter;
import com.loanuncle.gm.juke.util.SharePreferenceUtils;
import com.loanuncle.gm.juke.util.ToastUtils;
import com.loanuncle.gm.juke.view.ChatView;
import com.loanuncle.gm.juke.view.CommonDialog;
import com.loanuncle.gm.juke.view.fragment.BillFragment;
import com.loanuncle.gm.juke.view.fragment.ChartWebFragment;
import com.loanuncle.gm.juke.view.fragment.PersonCenterFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by GM on 2018/8/18.
 * @description 主界面
 */

public class HomeActivity extends LoanBaseActivity<SplashContact.presenter> implements SplashContact.view, View.OnClickListener, ChatView.ClickImageCallBack, CommonDialog.ICommonDialogClickCallBack {

    private RelativeLayout mBillBtnLayout;
    private RelativeLayout mLoanLayout;
    private RelativeLayout mMineBtnLayout;
    private RelativeLayout mAddBillLayout;

    private ImageView mBillBtn;
    private ImageView mMineBtn;
    private ImageView mChartBtn;
    private ChatView chatView;
    private CommonDialog mCommonDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        //步骤一：添加一个FragmentTransaction的实例
        FragmentManager fragmentManager = getSupportFragmentManager();
        BillFragment billFragment = BillFragment.newInstance(this);
        PersonCenterFragment personCenterFragment = PersonCenterFragment.newInstance(this);
//        fragmentManager.beginTransaction().add(R.id.fragment_layout, billFragment).commit();

        if (savedInstanceState == null) {
            loadRootFragment(R.id.fragment_layout, billFragment); //activity初始加载HomeFragment
        }

        String address = SharePreferenceUtils.readString(this,SharePreferenceUtils.ADDRESS);
        if(!TextUtils.isEmpty(address)){
            WebConstance.BASE_URL = address;
            ToastUtils.showShort(this,WebConstance.BASE_URL);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(OtherConstant.isFirstLoad){
            requestBaseInfo();
        }
    }

    @Override
    public void initView() {
        mBillBtnLayout = findViewById(R.id.bill_btn_layout);
        mLoanLayout = findViewById(R.id.loan_layout);
        mMineBtnLayout = findViewById(R.id.mine_btn_layout);
        mAddBillLayout = findViewById(R.id.add_bill_layout);
        mBillBtn = findViewById(R.id.bill_btn);
        mMineBtn = findViewById(R.id.mine_btn);
        mChartBtn = findViewById(R.id.add_bill_btn);

        mBillBtnLayout.setOnClickListener(this);
        mLoanLayout.setOnClickListener(this);
        mMineBtnLayout.setOnClickListener(this);
        mAddBillLayout.setOnClickListener(this);
        chatView = new ChatView(this,this);
        changeLoanShow();
    }

    @Override
    public SplashContact.presenter initPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bill_btn_layout:
                if(getTopFragment() instanceof BillFragment){
                    return;
                }else {
                    start(BillFragment.newInstance(HomeActivity.this), SupportFragment.SINGLETASK);
                    mBillBtn.setImageResource(R.mipmap.bill_click_btn);
                    mMineBtn.setImageResource(R.mipmap.mine_btn);
                    mChartBtn.setImageResource(R.mipmap.icon_chart);
                }
                break;
            case R.id.loan_layout:
                Intent intent = new Intent(HomeActivity.this,LoanWebActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_btn_layout:
                if(getTopFragment() instanceof PersonCenterFragment){
                    return;
                }else {
                    start(PersonCenterFragment.newInstance(HomeActivity.this),SupportFragment.SINGLETASK);
                }
                break;
            case R.id.add_bill_layout:
                if(UserConstant.LOGIN_STATUS){
                    start(ChartWebFragment.newInstance(HomeActivity.this), SupportFragment.SINGLETASK);
                }else {
                    Intent addIntent = new Intent(HomeActivity.this,LoginActivity.class);
                    startActivity(addIntent);
                }
                break;
                default:
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 获取版本信息请求
     * */
    private void requestBaseInfo(){
        BaseInfoRequestBean baseRequest = new BaseInfoRequestBean();
        presenter.getBaseInfoRequest(baseRequest);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onBackPressedSupport() {
        finish();
    }

    /**
     * 改变底部按钮背景
     * */
    private void changeBottomBtn(int type){
        if(type == 1){
            mBillBtn.setImageResource(R.mipmap.bill_click_btn);
            mMineBtn.setImageResource(R.mipmap.mine_btn);
            mChartBtn.setImageResource(R.mipmap.icon_chart);
        }else if(type == 2){
            mBillBtn.setImageResource(R.mipmap.bill_btn);
            mMineBtn.setImageResource(R.mipmap.mine_btn);
            mChartBtn.setImageResource(R.mipmap.icon_chart_choosed);
        }else {
            mBillBtn.setImageResource(R.mipmap.bill_btn);
            mMineBtn.setImageResource(R.mipmap.mine_click_btn);
            mChartBtn.setImageResource(R.mipmap.icon_chart);
        }
//        if(getTopFragment() instanceof BillFragment){
//            mBillBtn.setImageResource(R.mipmap.bill_btn);
//            mMineBtn.setImageResource(R.mipmap.mine_click_btn);
//        }else {
//            mBillBtn.setImageResource(R.mipmap.bill_click_btn);
//            mMineBtn.setImageResource(R.mipmap.mine_btn);
//        }
    }

    /**
     * 控制贷超是否显示
     * */
    private void changeLoanShow(){
        if(OtherConstant.SHOW_LOAN){
            mLoanLayout.setVisibility(View.VISIBLE);
            mAddBillLayout.setVisibility(View.GONE);
            chatView.show();
        }else {
            mLoanLayout.setVisibility(View.GONE);
            mAddBillLayout.setVisibility(View.VISIBLE);
            chatView.hide();
        }
    }

    /**
     * 弹出对话框
     * */
    public void showDialog(String content){
        mCommonDialog = new CommonDialog(this,content,this);
        if(VersionConstant.ISFORCE_UPDATE){
            mCommonDialog.setCanceledOnTouchOutside(false);
            mCommonDialog.setCancelable(false);
        }else {
            mCommonDialog.setCanceledOnTouchOutside(true);
            mCommonDialog.setCancelable(true);
        }

        mCommonDialog.show();
    }

    /**
     * 跳转下载更新包
     * */
    public void jumpToUpdate(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW); //Intent.ACTION_VIEW固定写法
        intent.setData(Uri.parse(url)); //url是你要跳转的网页地址
        startActivity(intent);
    }

    @Override
    public void getBaseInfoResponse(BaseInfoResponseBean baseInfoResponseBean) {
        String code = baseInfoResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){

            OtherConstant.SHOW_LOAN = baseInfoResponseBean.getResult().isShowLoans();
            OtherConstant.LOAN_URL = baseInfoResponseBean.getResult().getLoanUrl();
            OtherConstant.PUBLIC_NAME = baseInfoResponseBean.getResult().getWechatPublicName();
            OtherConstant.BILLCHART_URL = baseInfoResponseBean.getResult().getBillChartUrl();
            changeLoanShow();
            int serviceVersion = baseInfoResponseBean.getResult().getAppUpdateResultVo().getServiceVersion();
            VersionConstant.UPDATE_URL = baseInfoResponseBean.getResult().getAppUpdateResultVo().getUpdateUrl();
            if(baseInfoResponseBean.getResult().getAppUpdateResultVo().getUpdateType() == 1){
                VersionConstant.ISFORCE_UPDATE = true;
            }else {
                VersionConstant.ISFORCE_UPDATE = false;
            }
            if(serviceVersion > VersionConstant.VERSION_CODE){
                OtherConstant.isFirstLoad = false;
                showDialog(baseInfoResponseBean.getResult().getAppUpdateResultVo().getMessage());
                mCommonDialog.setBottomText("取消","我知道了");
                mCommonDialog.setHeadVisiable(true);
                if(VersionConstant.ISFORCE_UPDATE){
                    mCommonDialog.setDeleteGone();
                }
            }
        }else if(ResponseCodeConstant.CODE_401.equals(code)){
            overallLogout();
        }else if(ResponseCodeConstant.CODE_402.equals(code)){
            overallGetNewUserInfo();
        }
    }

    @Override
    public void floatClickCallBack() {
        Intent intent = new Intent(HomeActivity.this,LoanWebActivity.class);
        startActivity(intent);
    }

    @Override
    public void dialogDeleteCallBack() {
        mCommonDialog.dismiss();
        mCommonDialog = null;
    }

    @Override
    public void dialogSureCallBack() {
        mCommonDialog.dismiss();
        mCommonDialog = null;
        jumpToUpdate(VersionConstant.UPDATE_URL);
    }

    /**
     * 订阅eventbus当完成区域选择后
     *
     * @param statusEventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(ChangeButtonEventBus statusEventBus) {
        try {
            changeBottomBtn(statusEventBus.type);
        } catch (Exception e) {

        }
    }
}
