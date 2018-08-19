package com.loanuncle.gm.loanuncle.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loanuncle.gm.loanuncle.R;
import com.loanuncle.gm.loanuncle.adapter.ItemRemoveAdapter;
import com.loanuncle.gm.loanuncle.view.CommonDialog;
import com.loanuncle.gm.loanuncle.view.ItemRemoveRecyclerView;
import com.loanuncle.gm.loanuncle.view.OnItemRemoveClickListener;

/**
 * Created by GM on 2018/8/16.
 * @description 账单界面
 */

public class BillFragment extends Fragment implements OnItemRemoveClickListener, SwipeRefreshLayout.OnRefreshListener, CommonDialog.ICommonDialogClickCallBack {

    private Context mContext;
    private ItemRemoveRecyclerView mLoanBillList;
    private ItemRemoveAdapter itemRemoveAdapter;
    private SwipeRefreshLayout refreshLayout;
    private CommonDialog mCommonDialog;

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
        initView(view);
    }

    /**
     * 初始化
     * */
    private void initView(View view) {
        mLoanBillList = view.findViewById(R.id.loanbill_List);
        refreshLayout = view.findViewById(R.id.refresh);

        mLoanBillList.setLayoutManager(new LinearLayoutManager(getContext()));
        itemRemoveAdapter = new ItemRemoveAdapter(mContext);
        mLoanBillList.setAdapter(itemRemoveAdapter);
        mLoanBillList.setOnItemClickListener(this);

        refreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDeleteClick(int position) {
        showDialog();
    }

    @Override
    public void onRefresh() {

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

    @Override
    public void dialogDeleteCallBack() {
        mCommonDialog.dismiss();
    }

    @Override
    public void dialogSureCallBack() {
        mCommonDialog.dismiss();
    }
}
