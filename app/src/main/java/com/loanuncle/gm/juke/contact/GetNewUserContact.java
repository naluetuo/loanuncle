package com.loanuncle.gm.juke.contact;

import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.baselibrary.mvpbase.BaseView;
import com.loanuncle.gm.juke.bean.request.GetNewUserRequestBean;
import com.loanuncle.gm.juke.bean.response.GetNewUserResponseBean;

/**
 * Created by GM on 2018/8/25.
 * @description 验证用户信息接口
 */

public interface GetNewUserContact {

    interface view extends BaseView {

        /**
         * 验证用户信息返回
         * */
        void getNewUserInfoResponse(GetNewUserResponseBean getNewUserResponseBean);
    }

    interface presenter extends BasePresenter{

        /**
         * 验证用户信息请求
         * */
        void getNewUserInfoRequest(GetNewUserRequestBean getNewUserRequestBean);
    }
}
