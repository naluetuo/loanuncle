package com.loanuncle.gm.juke.contact;

import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.baselibrary.mvpbase.BaseView;
import com.loanuncle.gm.juke.bean.request.EditInformationRequestBean;
import com.loanuncle.gm.juke.bean.request.GetInformationRequestBean;
import com.loanuncle.gm.juke.bean.request.LogoutRequestBean;
import com.loanuncle.gm.juke.bean.response.EditInformationResponseBean;
import com.loanuncle.gm.juke.bean.response.GetInformationResponseBean;
import com.loanuncle.gm.juke.bean.response.LogoutResponseBean;

/**
 * Created by GM on 2018/9/16.
 * @description 个人资料界面的接口
 */

public interface InformationContact {

    interface view extends BaseView {

        /**
         * 验证个人信息返回
         * */
        void getInformationResponse(GetInformationResponseBean getInformationResponseBean);

        /**
         * 编辑个人信息返回
         * */
        void ediInformationResponse(EditInformationResponseBean editInformationResponseBean);

        /**
         * 退出登录返回接口
         * */
        void logoutResponse(LogoutResponseBean logoutResponseBean);
    }

    interface presenter extends BasePresenter {

        /**
         * 验证个人信息请求
         * */
        void getInformationRequest(GetInformationRequestBean getInformationRequestBean);

        /**
         * 编辑个人信息请求
         * */
        void editInformationRequest(EditInformationRequestBean editInformationRequestBean);

        /**
         * 退出登录请求接口
         * */
        void logoutRequest(LogoutRequestBean logoutRequestBean);
    }
}
