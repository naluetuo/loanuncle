package com.loanuncle.gm.juke.contact;

import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.baselibrary.mvpbase.BaseView;
import com.loanuncle.gm.juke.bean.request.GetBillDoneListRequestBean;
import com.loanuncle.gm.juke.bean.response.GetBillDoneListResponseBean;

/**
 * Created by GM on 2018/8/25.
 * @description 已完成账单页面接口配置
 */

public interface BillCompletedContact {

    interface view extends BaseView{

        /**
         * 获取已完成账单返回
         * */
        void getBillDoneListResponse(GetBillDoneListResponseBean getBillDoneListResponseBean);
    }

    interface presenter extends BasePresenter{

        /**
         * 获取已完成账单请求
         * */
        void getBillDoneListRequest(GetBillDoneListRequestBean getBillDoneListRequestBean);
    }
}
