package com.loanuncle.gm.juke.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.adapter.ItemRemoveAdapter;
import com.loanuncle.gm.juke.base.LoanBaseFragment;
import com.loanuncle.gm.juke.bean.request.DeleteBillRequestBean;
import com.loanuncle.gm.juke.bean.request.GetBillListRequestBean;
import com.loanuncle.gm.juke.bean.request.RepayMentRequestBean;
import com.loanuncle.gm.juke.bean.response.BillBean;
import com.loanuncle.gm.juke.bean.response.DeleteBillResponseBean;
import com.loanuncle.gm.juke.bean.response.GetBillListResponseBean;
import com.loanuncle.gm.juke.bean.response.GetNewUserResponseBean;
import com.loanuncle.gm.juke.bean.response.RepayMentResponseBean;
import com.loanuncle.gm.juke.constant.OtherConstant;
import com.loanuncle.gm.juke.constant.ResponseCodeConstant;
import com.loanuncle.gm.juke.constant.UserConstant;
import com.loanuncle.gm.juke.contact.BillContact;
import com.loanuncle.gm.juke.eventbus.AddBillEventBus;
import com.loanuncle.gm.juke.eventbus.ChangeButtonEventBus;
import com.loanuncle.gm.juke.presenter.BillListPresenter;
import com.loanuncle.gm.juke.util.ToastUtils;
import com.loanuncle.gm.juke.view.CommonDialog;
import com.loanuncle.gm.juke.view.ItemRemoveRecyclerView;
import com.loanuncle.gm.juke.view.OnItemRemoveClickListener;
import com.loanuncle.gm.juke.view.activity.AddBillActivity;
import com.loanuncle.gm.juke.view.activity.LoanWebActivity;
import com.loanuncle.gm.juke.view.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GM on 2018/8/16.
 * @description 账单界面
 */

public class BillFragment extends LoanBaseFragment<BillContact.presenter> implements OnItemRemoveClickListener,
        SwipeRefreshLayout.OnRefreshListener, CommonDialog.ICommonDialogClickCallBack, View.OnClickListener,
        BillContact.view, ItemRemoveAdapter.BillCallBack {

    private Context mContext;
    private ItemRemoveRecyclerView mLoanBillList;
    private ItemRemoveAdapter itemRemoveAdapter;
    private SwipeRefreshLayout refreshLayout;
    private CommonDialog mCommonDialog;

    private RelativeLayout mBlankLayout;
    private RelativeLayout mListLayout;
    private TextView addBillTxt;
    private TextView addBillBtn;

    private List<BillBean> mBillList;
    private int mDeletePosition;

    public static BillFragment newInstance(Context context){
        BillFragment fragment = new BillFragment();
        fragment.mContext = context;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        EventBus.getDefault().post(new ChangeButtonEventBus(1));
        initToolBar(view);
        requestBillList(1, OtherConstant.LIST_SIZE);
    }

    /**
     * 初始化
     * */
    @Override
    public void initView(View view) {
        mLoanBillList = view.findViewById(R.id.loanbill_List);
        refreshLayout = view.findViewById(R.id.refresh);
        mBlankLayout = view.findViewById(R.id.blank_layout);
        mListLayout = view.findViewById(R.id.list_layout);
        addBillTxt = view.findViewById(R.id.add_bill_txt);
        addBillBtn = view.findViewById(R.id.add_bill_btn);

        mBillList = new ArrayList<>();
        mLoanBillList.setLayoutManager(new LinearLayoutManager(getContext()));
        itemRemoveAdapter = new ItemRemoveAdapter(mContext,mBillList,this);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        View footerView = View.inflate(mContext, R.layout.footer, null);
        footerView.setLayoutParams(params);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddBillActivity.class);
                startActivity(intent);
            }
        });
        mLoanBillList.addFooterView(footerView);

        mLoanBillList.setAdapter(itemRemoveAdapter);
        mLoanBillList.setOnItemClickListener(this);

        addBillTxt.setOnClickListener(this);
        addBillBtn.setOnClickListener(this);

        refreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void initToolBar(View view) {
        super.initToolBar(view);
        mToolBarTitle.setText(mContext.getResources().getString(R.string.reminders_txt));
        mToolBarRightImage.setOnClickListener(this);
    }

    @Override
    public BillContact.presenter initPresenter() {
        return new BillListPresenter(this);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDeleteClick(int position) {
        mDeletePosition = position;
        showDialog();
    }

    @Override
    public void onRefresh() {
        refreshList();
    }

    @Override
    public void dialogDeleteCallBack() {
        mCommonDialog.dismiss();
    }

    @Override
    public void dialogSureCallBack() {
        mCommonDialog.dismiss();
        requestDeleteBill(mDeletePosition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_menu:
            case R.id.add_bill_txt:
            case R.id.add_bill_btn:
                if(UserConstant.LOGIN_STATUS){
                    Intent intent = new Intent(mContext, AddBillActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.float_layout:
                Intent intent = new Intent(mContext, LoanWebActivity.class);
                startActivity(intent);
                break;
                default:
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!UserConstant.LOGIN_STATUS){
            mBillList.clear();
            changeBackground();
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
     * 请求获取账单列表
     * */
    private void requestBillList(int page,int pageSize){
        GetBillListRequestBean getBillListRequestBean = new GetBillListRequestBean();
        getBillListRequestBean.setPageSize(pageSize);
        getBillListRequestBean.setRequestPage(page);
        presenter.getBillListRequest(getBillListRequestBean);
    }

    /**
     * 请求删除账单
     * */
    private void requestDeleteBill(int deletePosition){
        DeleteBillRequestBean deleteBillRequestBean = new DeleteBillRequestBean();
        BillBean billBean = mBillList.get(deletePosition);
        if(billBean != null){
            deleteBillRequestBean.setBillId(billBean.getBillId());
            presenter.deleteBillRequest(deleteBillRequestBean);
        }
    }

    /**
     * 请求账单还款
     * */
    private void requestRepayMent(BillBean billBean){
        RepayMentRequestBean repayMentRequestBean = new RepayMentRequestBean();
        if(billBean != null){
            repayMentRequestBean.setBillId(billBean.getBillId());
            presenter.repayMentRequest(repayMentRequestBean);
        }
    }

    /**
     * 弹出对话框
     * */
    public void showDialog(){
        mCommonDialog = new CommonDialog(getContext(),"确定删除该账单吗？",this);
        mCommonDialog.setCanceledOnTouchOutside(true);
        mCommonDialog.setCancelable(true);
        mCommonDialog.show();
    }

    /**
     * 列表为空显示空白页
     * */
    private void changeBackground(){
        boolean isShow = true;
        if(mBillList == null){
            isShow = false;
        }else{
            if(mBillList.size() == 0){
                isShow = false;
            }
        }
        if(isShow){
            mListLayout.setVisibility(View.VISIBLE);
            mBlankLayout.setVisibility(View.GONE);
        }else {
            mListLayout.setVisibility(View.GONE);
            mBlankLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 刷新列表
     * */
    private void refreshList(){
        mBillList.clear();
        mLoanBillList.getAdapter().notifyDataSetChanged();
        requestBillList(1, OtherConstant.LIST_SIZE);
    }

    @Override
    public void getBillListResponse(GetBillListResponseBean getBillListResponseBean) {
        String code = getBillListResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            mBillList.addAll(getBillListResponseBean.getResult().getBillList());
            mLoanBillList.getAdapter().notifyDataSetChanged();
        }else if(ResponseCodeConstant.CODE_401.equals(code)){
            overallLogout();
        }else if(ResponseCodeConstant.CODE_402.equals(code)){
            overallGetNewUserInfo();
        }
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        changeBackground();
    }

    @Override
    public void deleteBillResponse(DeleteBillResponseBean deleteBillResponseBean) {
        String code = deleteBillResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            mBillList.remove(mDeletePosition);
            mLoanBillList.getAdapter().notifyDataSetChanged();
            ToastUtils.showShort(mContext,"删除成功");
        }else if(ResponseCodeConstant.CODE_401.equals(code)){
            overallLogout();
        }else if(ResponseCodeConstant.CODE_402.equals(code)){
            overallGetNewUserInfo();
        }
        changeBackground();
    }

    @Override
    public void repayMentResponse(RepayMentResponseBean repayMentResponseBean) {
        String code = repayMentResponseBean.getReturnCode();
        if(ResponseCodeConstant.CODE_200.equals(code)){
            ToastUtils.showShort(mContext,"还款成功");
            refreshList();
        }else if(ResponseCodeConstant.CODE_401.equals(code)){
            overallLogout();
        }else if(ResponseCodeConstant.CODE_402.equals(code)){
            overallGetNewUserInfo();
        }
    }

    @Override
    public void makeSureRepayMent(BillBean bean) {
        requestRepayMent(bean);
    }

    /**
     * 订阅eventbus当完成区域选择后
     *
     * @param addBillEventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(AddBillEventBus addBillEventBus) {
        try {
            refreshList();
        } catch (Exception e) {

        }
    }

    @Override
    public void getNewUserInfoResponse(GetNewUserResponseBean getNewUserResponseBean) {
        super.getNewUserInfoResponse(getNewUserResponseBean);
//        refreshList();
    }

}
