package com.loanuncle.gm.juke.contact;

import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.baselibrary.mvpbase.BaseView;
import com.loanuncle.gm.juke.bean.request.AddBillRequestBean;
import com.loanuncle.gm.juke.bean.response.AddBillResponseBean;

/**
 * Created by GM on 2018/8/24.
 * @description 添加账单页面请求返回接口配置
 */

public interface AddBillContact {

    interface view extends BaseView {

        /**
         * 添加账单返回
         * */
        void addBillResponse(AddBillResponseBean addBillResponseBean);

    }

    interface presenter extends BasePresenter {

        /**
         * 添加账单请求
         * */
        void addBillRequest(AddBillRequestBean addBillRequestBean);
    }
}
