package com.loanuncle.gm.juke.contact;

import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.baselibrary.mvpbase.BaseView;
import com.loanuncle.gm.juke.bean.request.LogoutRequestBean;
import com.loanuncle.gm.juke.bean.response.LogoutResponseBean;

/**
 * Created by GM on 2018/8/23.
 * @description 个人中心接口配置
 */

public interface PersonCenterContact {

    interface view extends BaseView{

        /**
         * 退出登录返回接口
         * */
        void logoutResponse(LogoutResponseBean logoutResponseBean);
    }

    interface presenter extends BasePresenter{

        /**
         * 退出登录请求接口
         * */
        void logoutRequest(LogoutRequestBean logoutRequestBean);
    }
}
