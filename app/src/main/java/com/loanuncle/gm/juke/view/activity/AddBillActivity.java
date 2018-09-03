package com.loanuncle.gm.juke.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.base.LoanBaseActivity;
import com.loanuncle.gm.juke.bean.request.AddBillRequestBean;
import com.loanuncle.gm.juke.bean.response.AddBillResponseBean;
import com.loanuncle.gm.juke.constant.ResponseCodeConstant;
import com.loanuncle.gm.juke.contact.AddBillContact;
import com.loanuncle.gm.juke.eventbus.AddBillEventBus;
import com.loanuncle.gm.juke.presenter.AddBillPresenter;
import com.loanuncle.gm.juke.util.EditInputFilter;
import com.loanuncle.gm.juke.util.ObjectUtil;
import com.loanuncle.gm.juke.util.ToastUtils;
import com.loanuncle.gm.juke.view.CustomCurrentPeriodsPicker;
import com.loanuncle.gm.juke.view.CustomPeriodsPicker;
import com.loanuncle.gm.juke.view.CustomTimePicker;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by GM on 2018/8/18.
 * @description 添加账单界面
 */

public class AddBillActivity extends LoanBaseActivity<AddBillContact.presenter> implements AddBillContact.view, View.OnClickListener {

    private EditText mPlatformName;
    private EditText mUserName;
    private EditText mMonthlySupply;
    private RelativeLayout mRepayDateLayout;
    private TextView mRepayDate;
    private RelativeLayout mAllperiodsCountLayout;
    private TextView mAllperiodsCount;
    private RelativeLayout mCurrentperiodsCountLayout;
    private TextView mCurrentperiodsCount;
    private TextView mSaveBtn;
    private CustomPeriodsPicker mPeriodPicker;
    private CustomCurrentPeriodsPicker mCurrentPeriodPicker;
    private CustomTimePicker mTimePicker;

    private String platformStr;
    private String userNameStr;
    private String monthlySupplyStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_add_bill);
        super.onCreate(savedInstanceState);
        initToolBar();
    }

    @Override
    public AddBillContact.presenter initPresenter() {
        return new AddBillPresenter(this);
    }

    @Override
    public void initView() {
        mSaveBtn = findViewById(R.id.save_btn);
        mPlatformName = findViewById(R.id.platform_name);
        mUserName = findViewById(R.id.user_name);
        mMonthlySupply = findViewById(R.id.monthly_supply);
        mRepayDateLayout = findViewById(R.id.repay_date_layout);
        mRepayDate = findViewById(R.id.repay_date);
        mAllperiodsCountLayout = findViewById(R.id.allperiods_count_layout);
        mAllperiodsCount = findViewById(R.id.allperiods_count);
        mCurrentperiodsCountLayout = findViewById(R.id.currentperiods_count_layout);
        mCurrentperiodsCount = findViewById(R.id.currentperiods_count);

        mSaveBtn.setOnClickListener(this);
        mRepayDateLayout.setOnClickListener(this);
        mAllperiodsCountLayout.setOnClickListener(this);
        mCurrentperiodsCountLayout.setOnClickListener(this);

        InputFilter[] filters = { new EditInputFilter() };
        mMonthlySupply.setFilters(filters);
        initPicker();
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

    /**
     * 初始化选择器
     * */
    private void initPicker(){
        /**
         * 设置期数
         */
        mPeriodPicker = new CustomPeriodsPicker(this, new CustomPeriodsPicker.ResultHandler() {
            @Override
            public void handle(String data) {
                if(data != null){
                    mAllperiodsCount.setText(data);
                }
            }
        });
        mPeriodPicker.setIsLoop(false);
        mPeriodPicker.setUnit("期");
        mPeriodPicker.setMaxCount(24);
        mPeriodPicker.setPvTextSize(14);

        /**
         * 设置还款日
         */
        mTimePicker = new CustomTimePicker(this, new CustomTimePicker.ResultHandler() {
            @Override
            public void handle(String data) {
                if(data != null){
                    mRepayDate.setText(data);
                }
            }
        });
        mTimePicker.setIsLoop(false);
        mTimePicker.setUnit("日");
        mTimePicker.setMaxCount(31);
        mTimePicker.setPvTextSize(14);

        /**
         * 设置期数
         */
        mCurrentPeriodPicker = new CustomCurrentPeriodsPicker(this, new CustomCurrentPeriodsPicker.ResultHandler() {
            @Override
            public void handle(String data) {
                if(data != null){
                    mCurrentperiodsCount.setText(data);
                }
            }
        });
        mCurrentPeriodPicker.setIsLoop(false);
        mCurrentPeriodPicker.setUnit("期");
        mCurrentPeriodPicker.setMaxCount(24);
        mCurrentPeriodPicker.setPvTextSize(14);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_btn:
                if(checkEditEmpty()){
                    requestAddBill();
                }
                break;
            case R.id.repay_date_layout:
                mTimePicker.show(String.valueOf(mRepayDate.getText()));
                break;
            case R.id.allperiods_count_layout:
                mPeriodPicker.show(String.valueOf(mAllperiodsCount.getText()));
                break;
            case R.id.currentperiods_count_layout:
                mCurrentPeriodPicker.show(String.valueOf(mCurrentperiodsCount.getText()));
                break;
                default:
        }
    }

    /**
     * 保存前检查
     * */
    private boolean checkEditEmpty(){
        platformStr = String.valueOf(mPlatformName.getText());
        userNameStr = String.valueOf(mUserName.getText());
        monthlySupplyStr = String.valueOf(mMonthlySupply.getText());

        if(TextUtils.isEmpty(platformStr) || TextUtils.isEmpty(userNameStr) || TextUtils.isEmpty(monthlySupplyStr)){
            ToastUtils.showShort(this,"请把信息填写完整");
            return false;
        }else if(!ObjectUtil.isNumeric(monthlySupplyStr)){
            ToastUtils.showShort(this,"月供金额输入有误，请重新输入");
            return false;
        }else {
            return true;
        }
    }

    /**
     * 添加账单请求
     * */
    private void requestAddBill(){
        AddBillRequestBean addBillRequestBean = new AddBillRequestBean();

        addBillRequestBean.setBorrowChannel(platformStr);
        addBillRequestBean.setCurrentPeriods(cutDate(String.valueOf(mCurrentperiodsCount.getText())));
        addBillRequestBean.setDueDate(cutDate(String.valueOf(mRepayDate.getText())));
        addBillRequestBean.setMonthDueAmount(Integer.parseInt(monthlySupplyStr));
        addBillRequestBean.setTotalPeriods(cutDate(String.valueOf(mAllperiodsCount.getText())));
        addBillRequestBean.setUsername(userNameStr);

        presenter.addBillRequest(addBillRequestBean);
    }

    /**
     * 截取方法
     * */
    private int cutDate(String data){
        if(TextUtils.isEmpty(data)){
            return 0;
        }else {
            String cutStr = data.substring(0,data.length()-1);
            return Integer.parseInt(cutStr);
        }
    }

    @Override
    public void addBillResponse(AddBillResponseBean addBillResponseBean) {
        String code = addBillResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            EventBus.getDefault().post(new AddBillEventBus());
            finish();
        }else if(ResponseCodeConstant.CODE_401.equals(code)){
            overallLogout();
        }else if(ResponseCodeConstant.CODE_402.equals(code)){
            overallGetNewUserInfo();
        }
    }
}
