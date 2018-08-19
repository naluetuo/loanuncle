package com.loanuncle.gm.loanuncle.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loanuncle.gm.loanuncle.R;
import com.loanuncle.gm.loanuncle.view.CommonDialog;

/**
 * Created by GM on 2018/8/18.
 * @description 个人中心页面
 */

public class PersonCenterFragment extends Fragment implements CommonDialog.ICommonDialogClickCallBack {

    private Context mContext;
    private TextView mLoginCancleBtn;
    private CommonDialog mCommonDialog;

    public static PersonCenterFragment newInstance(Context context){
        PersonCenterFragment fragment = new PersonCenterFragment();
        fragment.mContext = context;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personcenter,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mLoginCancleBtn = view.findViewById(R.id.login_cancle);
        mLoginCancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    /**
     * 弹出对话框
     * */
    public void showDialog(){
        mCommonDialog = new CommonDialog(getContext(),"",this);
        mCommonDialog.setCanceledOnTouchOutside(true);
        mCommonDialog.setCancelable(true);
        mCommonDialog.show();

//        TextView title = mCommonDialog.findViewById(R.id.login_title);
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
