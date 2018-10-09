package com.loanuncle.gm.juke.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.juke.R;
import com.loanuncle.gm.juke.base.LoanBaseActivity;
import com.loanuncle.gm.juke.constant.WebConstance;
import com.loanuncle.gm.juke.util.SharePreferenceUtils;

/**
 * Created by GM on 2018/9/4.
 * @description 环境配置界面
 */

public class AddressChangeActivity extends LoanBaseActivity {

    private EditText mEnvironmentEdt;
    private TextView mSureText;
    private String addressStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_address_change);
        super.onCreate(savedInstanceState);
        initToolBar();
    }

    @Override
    public void initView() {
        mEnvironmentEdt = findViewById(R.id.environment_edt);
        mSureText = findViewById(R.id.sure_btn);

        mSureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressStr = String.valueOf(mEnvironmentEdt.getText());
                if(TextUtils.isEmpty(addressStr)){
                    Toast.makeText(context, "输入地址为空", Toast.LENGTH_SHORT).show();
                }else {
                    WebConstance.BASE_URL = "http://"+addressStr+"/";
                    SharePreferenceUtils.saveObject(AddressChangeActivity.this,SharePreferenceUtils.ADDRESS,WebConstance.BASE_URL);
                    Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        mToolBarRightImage.setVisibility(View.GONE);
        toolbar.setNavigationIcon(R.mipmap.back);
        mToolBarTitle.setText(getResources().getString(R.string.environmental));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
