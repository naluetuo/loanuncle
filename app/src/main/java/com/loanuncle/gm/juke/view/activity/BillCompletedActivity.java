package com.loanuncle.gm.juke.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.adapter.BillCompltedAdapter;
import com.loanuncle.gm.juke.base.LoanBaseActivity;
import com.loanuncle.gm.juke.bean.request.GetBillDoneListRequestBean;
import com.loanuncle.gm.juke.bean.response.BillCompletedBean;
import com.loanuncle.gm.juke.bean.response.GetBillDoneListResponseBean;
import com.loanuncle.gm.juke.bean.response.GetNewUserResponseBean;
import com.loanuncle.gm.juke.constant.OtherConstant;
import com.loanuncle.gm.juke.constant.ResponseCodeConstant;
import com.loanuncle.gm.juke.contact.BillCompletedContact;
import com.loanuncle.gm.juke.presenter.BillDoneListPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GM on 2018/8/18.
 * @description 已完成账单界面
 */

public class BillCompletedActivity extends LoanBaseActivity<BillCompletedContact.presenter> implements BillCompletedContact.view {

    private RelativeLayout mBlankLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView alreadyPayList;
    private BillCompltedAdapter mAdapter;

    private List<BillCompletedBean> billCompletedBeans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bill_completed);
        super.onCreate(savedInstanceState);

        initToolBar();
        requestBillCompletedList(1, OtherConstant.LIST_SIZE);
    }

    @Override
    public BillCompletedContact.presenter initPresenter() {
        return new BillDoneListPresenter(this);
    }

    @Override
    public void initView() {
        alreadyPayList = findViewById(R.id.alreadybill_List);
        mBlankLayout = findViewById(R.id.blank_layout);
        refreshLayout = findViewById(R.id.refresh);
        billCompletedBeans = new ArrayList<>();
        alreadyPayList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BillCompltedAdapter(this,billCompletedBeans);
        alreadyPayList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        mToolBarRightImage.setVisibility(View.GONE);
        mToolBarTitle.setText(getResources().getString(R.string.bill_completed));
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 请求已完成列表
     * */
    private void requestBillCompletedList(int page,int pageSize){
        GetBillDoneListRequestBean getBillDoneListRequestBean = new GetBillDoneListRequestBean();
        getBillDoneListRequestBean.setPageSize(pageSize);
        getBillDoneListRequestBean.setRequestPage(page);
        presenter.getBillDoneListRequest(getBillDoneListRequestBean);
    }

    /**
     * 列表为空显示空白页
     * */
    private void changeBackground(boolean isShow){
        if(isShow){
            refreshLayout.setVisibility(View.VISIBLE);
            mBlankLayout.setVisibility(View.GONE);
        }else {
            refreshLayout.setVisibility(View.GONE);
            mBlankLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 刷新列表
     * */
    private void refreshList(){
        billCompletedBeans.clear();
        requestBillCompletedList(1,OtherConstant.LIST_SIZE);
    }

    @Override
    public void getBillDoneListResponse(GetBillDoneListResponseBean getBillDoneListResponseBean) {
        String code = getBillDoneListResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            List<BillCompletedBean> beans = getBillDoneListResponseBean.getResult().getBillCompletedList();
            if(beans != null){
                billCompletedBeans.addAll(beans);
                mAdapter.notifyDataSetChanged();
            }
        }else if(ResponseCodeConstant.CODE_401.equals(code)){
            overallLogout();
        }else if(ResponseCodeConstant.CODE_402.equals(code)){
            overallGetNewUserInfo();
        }
        if(billCompletedBeans.size() == 0){
            changeBackground(false);
        }else {
            changeBackground(true);
        }
    }

    @Override
    public void getNewUserInfoResponse(GetNewUserResponseBean getNewUserResponseBean) {
        super.getNewUserInfoResponse(getNewUserResponseBean);
//        refreshList();
    }
}
