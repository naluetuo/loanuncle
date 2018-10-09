package com.loanuncle.gm.juke.view.fragment;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.base.LoanBaseFragment;
import com.loanuncle.gm.juke.bean.request.FeedBackRequestBean;
import com.loanuncle.gm.juke.bean.request.LogoutRequestBean;
import com.loanuncle.gm.juke.bean.response.FeedBackResponseBean;
import com.loanuncle.gm.juke.bean.response.LogoutResponseBean;
import com.loanuncle.gm.juke.constant.OtherConstant;
import com.loanuncle.gm.juke.constant.ResponseCodeConstant;
import com.loanuncle.gm.juke.constant.UserConstant;
import com.loanuncle.gm.juke.constant.VersionConstant;
import com.loanuncle.gm.juke.contact.PersonCenterContact;
import com.loanuncle.gm.juke.eventbus.ChangeButtonEventBus;
import com.loanuncle.gm.juke.eventbus.StatusEventBus;
import com.loanuncle.gm.juke.presenter.PersonCenterPresenter;
import com.loanuncle.gm.juke.util.SharePreferenceUtils;
import com.loanuncle.gm.juke.util.ToastUtils;
import com.loanuncle.gm.juke.view.CommonDialog;
import com.loanuncle.gm.juke.view.FeedBackDialog;
import com.loanuncle.gm.juke.view.activity.BillCompletedActivity;
import com.loanuncle.gm.juke.view.activity.InformationActivity;
import com.loanuncle.gm.juke.view.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by GM on 2018/8/18.
 * @description 个人中心页面
 */

public class PersonCenterFragment extends LoanBaseFragment<PersonCenterContact.presenter> implements
        CommonDialog.ICommonDialogClickCallBack, View.OnClickListener, PersonCenterContact.view, FeedBackDialog.IFeedBackDialogClickCallBack {

    private Context mContext;
//    private TextView mLoginCancleBtn;
    private TextView mLoginTxt;
    private RelativeLayout mPublicLayout;
    private CommonDialog mCommonDialog;
    private CommonDialog mPublicDialog;
    private FeedBackDialog mFeedBackDialog;
    private CommonDialog mHelpDialog;

    private RelativeLayout mBillLayout;
    private RelativeLayout mInformationLayout;
    private RelativeLayout mFeedbackLayout;
    private RelativeLayout mHelpLayout;
    private RelativeLayout mAboutLayout;
    private TextView mPublicName;
    private TextView mAboutNumber;

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
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        EventBus.getDefault().post(new ChangeButtonEventBus(3));
        initToolBar(view);
    }

    @Override
    public void initView(View view) {
//        mLoginCancleBtn = view.findViewById(R.id.login_cancle);
        mBillLayout = view.findViewById(R.id.bill_layout);
        mLoginTxt = view.findViewById(R.id.login_txt);
        mPublicLayout = view.findViewById(R.id.public_layout);
        mInformationLayout = view.findViewById(R.id.information_layout);
        mFeedbackLayout = view.findViewById(R.id.feedback_layout);
        mHelpLayout = view.findViewById(R.id.help_layout);
        mAboutLayout = view.findViewById(R.id.about_layout);
        mPublicName = view.findViewById(R.id.public_number);
        mAboutNumber = view.findViewById(R.id.about_number);

//        mLoginCancleBtn.setOnClickListener(this);
        mBillLayout.setOnClickListener(this);
        mLoginTxt.setOnClickListener(this);
        mInformationLayout.setOnClickListener(this);
        mFeedbackLayout.setOnClickListener(this);
        mHelpLayout.setOnClickListener(this);
        mAboutLayout.setOnClickListener(this);

        if(OtherConstant.SHOW_LOAN){
            mPublicLayout.setVisibility(View.VISIBLE);
        }else {
            mPublicLayout.setVisibility(View.GONE);
        }
        mPublicName.setText(OtherConstant.PUBLIC_NAME);
        mAboutNumber.setText(VersionConstant.VERSION_NAME);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
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
     * 弹出意见反馈对话框
     * */
    public void showFeedBackDialog(){
        mFeedBackDialog = new FeedBackDialog(getContext(),mContext.getResources().getString(R.string.feedback),this);
        mFeedBackDialog.setCanceledOnTouchOutside(true);
        mFeedBackDialog.setCancelable(true);
        mFeedBackDialog.show();
    }

    /**
     * 弹出帮助对话框
     * */
    public void showHelpDialog(){
        String title = mContext.getResources().getString(R.string.help_content);
        mHelpDialog = new CommonDialog(getContext(),title,this);
        mHelpDialog.setCanceledOnTouchOutside(true);
        mHelpDialog.setCancelable(true);
        mHelpDialog.show();
        mHelpDialog.setBottomText("取消","去关注");
        mHelpDialog.setHeadVisiable(true);
        mHelpDialog.setHead(mContext.getResources().getString(R.string.help));

        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.BLUE);
        SpannableStringBuilder builder = new SpannableStringBuilder(title);
        builder.setSpan(redSpan, 4, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mHelpDialog.setTitleStr(builder);

    }

    /**
     * 登录状态和未登录状态界面切换
     * */
    private void updateView(boolean login){
        if(login){
//            mLoginCancleBtn.setVisibility(View.VISIBLE);
            mLoginTxt.setText(UserConstant.MOBILE);
        }else {
//            mLoginCancleBtn.setVisibility(View.GONE);
            mLoginTxt.setText(mContext.getResources().getString(R.string.login_immediately));
        }
    }

    /**
     * 跳转到微信
     */
    private void getWechatApi(){
        ClipboardManager tvCopy = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        tvCopy.setText(getResources().getString(R.string.public_name));
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
        if(mFeedBackDialog != null){
            mFeedBackDialog.dismiss();
            mFeedBackDialog = null;
        }
        if(mHelpDialog != null){
            mHelpDialog.dismiss();
            mHelpDialog = null;
        }
    }

    @Override
    public void dialogSubmitCallBack(String feedBackStr) {
        if(mFeedBackDialog != null){
            mFeedBackDialog.dismiss();
            mFeedBackDialog = null;
        }
        FeedBackRequestBean feedBackRequestBean = new FeedBackRequestBean();
        feedBackRequestBean.setDescribe(feedBackStr);
        presenter.feedBackRequest(feedBackRequestBean);
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
        if(mHelpDialog != null){
            mHelpDialog.dismiss();
            mHelpDialog = null;
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
                if(!UserConstant.LOGIN_STATUS){
                    Intent intent1 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent1);
                }
                break;
//            case R.id.public_layout:
//                showPublicDialog();
//                mPublicDialog.setBottomText("朕考虑一下","朕去看看");
//                break;
            case R.id.information_layout:
                if(UserConstant.LOGIN_STATUS){
                    Intent intent = new Intent(mContext, InformationActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.feedback_layout:
                if(UserConstant.LOGIN_STATUS){
                    showFeedBackDialog();
                }else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.help_layout:
                showHelpDialog();
                break;
            case R.id.about_layout:
                break;
                default:
        }
    }

    /**
     * 订阅eventbus当完成区域选择后
     *
     * @param statusEventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(StatusEventBus statusEventBus) {
        try {
            updateView(false);
        } catch (Exception e) {

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

    @Override
    public void feedBackResponse(FeedBackResponseBean feedBackResponseBean) {
        String code = feedBackResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            ToastUtils.showShort(mContext,mContext.getResources().getString(R.string.feedback_content));
        }else if(ResponseCodeConstant.CODE_401.equals(code)){
            overallLogout();
        }else if(ResponseCodeConstant.CODE_402.equals(code)){
            overallGetNewUserInfo();
        }
    }

}
