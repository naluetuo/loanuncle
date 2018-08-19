package com.loanuncle.gm.loanuncle.view.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.loanuncle.gm.loanuncle.R;
import com.loanuncle.gm.loanuncle.view.fragment.BillFragment;
import com.loanuncle.gm.loanuncle.view.fragment.PersonCenterFragment;

/**
 * Created by GM on 2018/8/18.
 * @description 主界面
 */

public class HomeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //步骤一：添加一个FragmentTransaction的实例
        FragmentManager fragmentManager = getSupportFragmentManager();
        BillFragment billFragment = BillFragment.newInstance(this);
        PersonCenterFragment personCenterFragment = PersonCenterFragment.newInstance(this);
        fragmentManager.beginTransaction().add(R.id.fragment_layout, billFragment).commit();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//步骤二：用add()方法加上Fragment的对象ListFragment
//        BillFragment billFragment = BillFragment.newInstance(this);
//        transaction.add(R.id.fragment_layout, billFragment);
//步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
//        transaction.commit();
    }
}
