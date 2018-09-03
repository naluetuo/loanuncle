package com.loanuncle.gm.juke.contact;

import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.baselibrary.mvpbase.BaseView;
import com.loanuncle.gm.juke.bean.request.AgreementRequestBean;
import com.loanuncle.gm.juke.bean.request.LoginRequestBean;
import com.loanuncle.gm.juke.bean.request.VerCodeRequestBean;
import com.loanuncle.gm.juke.bean.response.AgreementResponseBean;
import com.loanuncle.gm.juke.bean.response.LoginResponseBean;
import com.loanuncle.gm.juke.bean.response.VerCodeResponseBean;

/**
 * Created by GM on 2018/8/21.
 * @description 登录页面请求返回接口配置
 */

public interface LoginContact {

    interface view extends BaseView{

        /**
         * 获取验证码返回值
         * */
        void verCodeResponse(VerCodeResponseBean verCodeResponseBean);

        /**
         * 登录请求返回值
         * */
        void loginResponse(LoginResponseBean loginResponseBean);

        /**
         * 协议请求返回类
         * */
        void agreementResponse(AgreementResponseBean agreementResponseBean);
    }

    interface presenter extends BasePresenter {

        /**
         * 请求获取验证码
         * */
        void requestVerCode(VerCodeRequestBean verCodeRequestBean);

        /**
         * 登录请求
         * */
        void requestLogin(LoginRequestBean loginRequestBean);

        /**
         * 协议请求
         * */
        void requestAgreement(AgreementRequestBean agreementRequestBean);
    }
}
