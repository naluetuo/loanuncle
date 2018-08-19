package com.loanuncle.gm.loanuncle.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loanuncle.gm.loanuncle.R;
import com.loanuncle.gm.loanuncle.view.CustomPeriodsPicker;

/**
 * Created by GM on 2018/8/18.
 * @description 添加账单界面
 */

public class AddBillActivity extends AppCompatActivity{

    private TextView mSaveBtn;
    private CustomPeriodsPicker mPeriodPicker;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        mSaveBtn = findViewById(R.id.save_btn);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPeriodPicker.show("5期");
            }
        });

        /**
         * 设置年月日
         */
        mPeriodPicker = new CustomPeriodsPicker(this, new CustomPeriodsPicker.ResultHandler() {
            @Override
            public void handle(String data) {
                if(data != null){
                    Toast.makeText(AddBillActivity.this,data,Toast.LENGTH_SHORT).show();
                }
            }
        });
        mPeriodPicker.setIsLoop(false);
        mPeriodPicker.setUnit("期");
        mPeriodPicker.setMaxCount(12);
        mPeriodPicker.setPvTextSize(14);
    }
}
