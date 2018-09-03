package com.loanuncle.gm.juke.contact;

import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.baselibrary.mvpbase.BaseView;
import com.loanuncle.gm.juke.bean.request.BaseInfoRequestBean;
import com.loanuncle.gm.juke.bean.response.BaseInfoResponseBean;

/**
 * Created by GM on 2018/8/25.
 * @description 闪屏界面接口配置
 */

public interface SplashContact {

    interface view extends BaseView{

        /**
         * 获取版本信息返回
         * */
        void getBaseInfoResponse(BaseInfoResponseBean baseInfoResponseBean);
    }

    interface presenter extends BasePresenter {

        /**
         * 获取版本信息请求
         * */
        void getBaseInfoRequest(BaseInfoRequestBean baseRequest);
    }
}
