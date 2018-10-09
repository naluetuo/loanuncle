package com.loanuncle.gm.juke.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.base.LoanBaseActivity;
import com.loanuncle.gm.juke.bean.request.EditInformationRequestBean;
import com.loanuncle.gm.juke.bean.request.GetInformationRequestBean;
import com.loanuncle.gm.juke.bean.request.LogoutRequestBean;
import com.loanuncle.gm.juke.bean.response.EditInformationResponseBean;
import com.loanuncle.gm.juke.bean.response.GetInformationResponseBean;
import com.loanuncle.gm.juke.constant.ResponseCodeConstant;
import com.loanuncle.gm.juke.contact.InformationContact;
import com.loanuncle.gm.juke.presenter.InformationPresenter;
import com.loanuncle.gm.juke.util.DateUtil;
import com.loanuncle.gm.juke.util.ToastUtils;
import com.loanuncle.gm.juke.view.ChangeNameDialog;
import com.loanuncle.gm.juke.view.CommonDialog;
import com.loanuncle.gm.juke.view.PickTimeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by GM on 2018/9/16.
 * @description 个人信息Activity
 */

public class InformationActivity extends LoanBaseActivity<InformationContact.presenter> implements View.OnClickListener,
        PickTimeView.onSelectedChangeListener, InformationContact.view, ChangeNameDialog.INameDialogClickCallBack, CommonDialog.ICommonDialogClickCallBack {

    private RelativeLayout mPhonenumberLayout;
    private RelativeLayout mNamenumberLayout;
    private RelativeLayout mGendernumberLayout;
    private RelativeLayout mBirthdaynumberLayout;

    private TextView mPhoneNumber;
    private TextView mName;
    private TextView mGender;
    private TextView mBirthday;
    private TextView mLoginCancle;

    private View emptyView1;
    private View emptyView2;

    private TextView mDateCancleBtn;
    private TextView mDateSureBtn;
    private PickTimeView picker;
    private ConstraintLayout mPickeeLayout;
    private SimpleDateFormat sdfDate;
    private String dateStr;

    private CommonDialog mCommonDialog;
    private ChangeNameDialog changeNameDialog;
    /** 壁纸选择的布局 */
    private PopupWindow imgPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_information);
        super.onCreate(savedInstanceState);
        initToolBar();
        getInformation();
    }

    @Override
    public void initView() {
        mPhonenumberLayout = findViewById(R.id.phonenumber_layout);
        mNamenumberLayout = findViewById(R.id.name_layout);
        mGendernumberLayout = findViewById(R.id.gender_layout);
        mBirthdaynumberLayout = findViewById(R.id.birthday_layout);
        mPhoneNumber = findViewById(R.id.phone_number);
        mName = findViewById(R.id.name_txt);
        mGender = findViewById(R.id.gender_txt);
        mBirthday = findViewById(R.id.birthday_txt);
        mPickeeLayout = findViewById(R.id.picker_layout);
        emptyView1 = findViewById(R.id.empty_layout1);
        emptyView2 = findViewById(R.id.empty_layout2);
        mDateCancleBtn = findViewById(R.id.datecancle_btn);
        mDateSureBtn = findViewById(R.id.datesure_btn);
        mLoginCancle = findViewById(R.id.login_cancle);
        picker = findViewById(R.id.picker);
        picker.setViewType(PickTimeView.TYPE_PICK_DATE);
        picker.setOnSelectedChangeListener(this);

        mNamenumberLayout.setOnClickListener(this);
        mGendernumberLayout.setOnClickListener(this);
        mBirthdaynumberLayout.setOnClickListener(this);
        emptyView1.setOnClickListener(this);
        emptyView2.setOnClickListener(this);
        mDateCancleBtn.setOnClickListener(this);
        mDateSureBtn.setOnClickListener(this);
        mLoginCancle.setOnClickListener(this);

        sdfDate = new SimpleDateFormat("yyyy.MM.dd");
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        mToolBarRightImage.setVisibility(View.GONE);
        mToolBarTitle.setText(getResources().getString(R.string.add_bill));
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public InformationContact.presenter initPresenter() {
        return new InformationPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.name_layout:
                showNameDialog();
                break;
            case R.id.gender_layout:
                showImgeWindow(this);
                break;
            case R.id.birthday_layout:
                try {
                    long timeMill = DateUtil.stringToLong(String.valueOf(mBirthday.getText()),"yyyy.MM.dd");
                    if(timeMill != 0){
                        picker.setTimeMillis(timeMill);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mPickeeLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.empty_layout1:
                mPickeeLayout.setVisibility(View.GONE);
                break;
            case R.id.empty_layout2:
                mPickeeLayout.setVisibility(View.GONE);
                break;
            case R.id.datecancle_btn:
                mPickeeLayout.setVisibility(View.GONE);
                break;
            case R.id.datesure_btn:
                mPickeeLayout.setVisibility(View.GONE);
                mBirthday.setText(dateStr);
                editInformation();
                break;
            case R.id.login_cancle:
                showDialog();
                break;
                default:
        }
    }

    /**
     * 利用反射获取状态栏高度
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onSelected(PickTimeView view, long timeMillis) {
        dateStr = sdfDate.format(timeMillis);
    }

    /**
     * 显示姓名编辑框
     * */
    private void showNameDialog(){
        changeNameDialog = new ChangeNameDialog(this,"姓名",this);
        changeNameDialog.show();
    }

    /**
     * 弹出对话框
     * */
    public void showDialog(){
        mCommonDialog = new CommonDialog(this,getResources().getString(R.string.is_goback),this);
        mCommonDialog.setCanceledOnTouchOutside(true);
        mCommonDialog.setCancelable(true);
        mCommonDialog.show();
    }

    /**
     * 显示相册或者壁纸的popupwindow
     * */
    private void showImgeWindow(Context context){
        imgPopupWindow=new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View view= LayoutInflater.from(context).inflate(R.layout.popwindow_select_photo,null);

        TextView man=(TextView)view.findViewById(R.id.man);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPopupWindow.dismiss();
                mGender.setText("MAN");
                editInformation();
            }
        });
        TextView woman=(TextView)view.findViewById(R.id.woman);
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPopupWindow.dismiss();
                mGender.setText("WOMAN");
                editInformation();
            }
        });
        TextView cancel=(TextView)view.findViewById(R.id.delete);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPopupWindow.dismiss();
            }
        });

        imgPopupWindow.setContentView(view);
        imgPopupWindow.setFocusable(true);
        imgPopupWindow.setOutsideTouchable(true);
        imgPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        imgPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        imgPopupWindow.update();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.6f;
        getWindow().setAttributes(params);
        imgPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    @Override
    public void dialogDeleteCallBack() {
        if(changeNameDialog != null){
            changeNameDialog.dismiss();
            changeNameDialog = null;
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
    }

    @Override
    public void dialogSureNameCallBack(String nameStr) {
        if(changeNameDialog != null){
            changeNameDialog.dismiss();
            changeNameDialog = null;
        }
        mName.setText(nameStr);
//        closeSoftInput();
        editInformation();
    }

    /**
     * 关闭软键盘
     * */
    private void closeSoftInput(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
        if (imm.isActive()) {//如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    /**
     * 获取个人信息
     * */
    private void getInformation(){
        GetInformationRequestBean getInformationRequestBean = new GetInformationRequestBean();
        presenter.getInformationRequest(getInformationRequestBean);
    }

    /**
     * 编辑个人信息
     * */
    private void editInformation(){
        EditInformationRequestBean editInformationRequestBean = new EditInformationRequestBean();
        String birthStr = String.valueOf(mBirthday.getText());
        String requestBirth = birthStr.replaceAll("\\.","");
        editInformationRequestBean.setBirthday(requestBirth);
        editInformationRequestBean.setNickname(String.valueOf(mName.getText()));
        editInformationRequestBean.setSex(String.valueOf(mGender.getText()));
        presenter.editInformationRequest(editInformationRequestBean);
    }

    /**
     * 设置数据
     * */
    private void setTextViewData(GetInformationResponseBean getInformationResponseBean){
        if(getInformationResponseBean != null){
            mPhoneNumber.setText(getInformationResponseBean.getResult().getMobile().trim());
            mName.setText(getInformationResponseBean.getResult().getNickname());
            mGender.setText(getInformationResponseBean.getResult().getSex());
            String birthDayStr = getInformationResponseBean.getResult().getBirthday();
            String showBirthStr = birthDayStr.substring(0,4)+"."+birthDayStr.substring(4,6)+"."+birthDayStr.substring(6,birthDayStr.length());
            dateStr = showBirthStr;
            mBirthday.setText(showBirthStr);
        }
    }

    @Override
    public void getInformationResponse(GetInformationResponseBean getInformationResponseBean) {
        String code = getInformationResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            setTextViewData(getInformationResponseBean);
        }else if(ResponseCodeConstant.CODE_401.equals(code)){
            overallLogout();
        }else if(ResponseCodeConstant.CODE_402.equals(code)){
            overallGetNewUserInfo();
        }
    }

    @Override
    public void ediInformationResponse(EditInformationResponseBean editInformationResponseBean) {
        String code = editInformationResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            ToastUtils.showShort(InformationActivity.this,"修改成功");
        }else if(ResponseCodeConstant.CODE_401.equals(code)){
            overallLogout();
        }else if(ResponseCodeConstant.CODE_402.equals(code)){
            overallGetNewUserInfo();
        }
    }

    @Override
    public void onBackPressedSupport() {
        if(mPickeeLayout.getVisibility() == View.VISIBLE){
            mPickeeLayout.setVisibility(View.GONE);
        }else if(changeNameDialog != null && changeNameDialog.isShowing()){
            changeNameDialog.dismiss();
            changeNameDialog = null;
        }else if(mCommonDialog != null && mCommonDialog.isShowing()){
            mCommonDialog.dismiss();
            mCommonDialog = null;
        }else if(imgPopupWindow != null && imgPopupWindow.isShowing()){
            imgPopupWindow.dismiss();
            imgPopupWindow = null;
        }else {
            finish();
        }
    }
}
