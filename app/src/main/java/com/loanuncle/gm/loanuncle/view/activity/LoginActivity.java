package com.loanuncle.gm.loanuncle.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.loanuncle.gm.loanuncle.R;

/**
 * Created by GM on 2018/8/16.
 * @description 登录界面
 */

public class LoginActivity extends AppCompatActivity{

    private TextView mLoginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);

        mLoginBtn = findViewById(R.id.login_btn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,BillCompletedActivity.class);
                startActivity(intent);
            }
        });
    }
}
