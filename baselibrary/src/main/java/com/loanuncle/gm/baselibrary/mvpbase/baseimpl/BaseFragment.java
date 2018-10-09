package com.loanuncle.gm.baselibrary.mvpbase.baseimpl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.loanuncle.gm.baselibrary.mvpbase.BaseView;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * Created by GM on 2018/8/20.
 * @description 基础Fragment
 */
public abstract class BaseFragment extends SupportFragment implements BaseView {

    private boolean isViewCreate = false;//view是否创建
    private boolean isViewVisible = false;//view是否可见
    public Context context;
    private boolean isFirst = true;//是否第一次加载


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreate = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisible = isVisibleToUser;
        if (isVisibleToUser && isViewCreate) {
            visibleToUser();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isViewVisible) {
            visibleToUser();
        }
    }

    /**
     * 懒加载
     * 让用户可见
     * 第一次加载
     */
    protected void firstLoad() {

    }

    /**
     * 懒加载
     * 让用户可见
     */
    protected void visibleToUser() {
        if (isFirst) {
            firstLoad();
            isFirst = false;
        }
    }


    @Override
    public void onDestroyView() {
        isViewCreate = false;
        super.onDestroyView();
    }


}
