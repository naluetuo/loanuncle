package com.loanuncle.gm.loanuncle.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.loanuncle.gm.loanuncle.R;
import com.loanuncle.gm.loanuncle.adapter.BillCompltedAdapter;

/**
 * Created by GM on 2018/8/18.
 * @description 已完成账单界面
 */

public class BillCompletedActivity extends AppCompatActivity{

    private RecyclerView alreadyPayList;
    private BillCompltedAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_completed);
        initView();
    }

    private void initView() {
        alreadyPayList = findViewById(R.id.alreadybill_List);
        alreadyPayList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BillCompltedAdapter(this);
        alreadyPayList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
