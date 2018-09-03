package com.loanuncle.gm.juke.contact;

import com.loanuncle.gm.baselibrary.mvpbase.BasePresenter;
import com.loanuncle.gm.baselibrary.mvpbase.BaseView;
import com.loanuncle.gm.juke.bean.request.DeleteBillRequestBean;
import com.loanuncle.gm.juke.bean.request.GetBillListRequestBean;
import com.loanuncle.gm.juke.bean.request.RepayMentRequestBean;
import com.loanuncle.gm.juke.bean.response.DeleteBillResponseBean;
import com.loanuncle.gm.juke.bean.response.GetBillListResponseBean;
import com.loanuncle.gm.juke.bean.response.RepayMentResponseBean;

/**
 * Created by GM on 2018/8/24.
 * @description 账单页面请求返回接口配置
 */

public interface BillContact {

    interface view extends BaseView{

        /**
         * 获取账单列表返回
         * */
        void getBillListResponse(GetBillListResponseBean getBillListResponseBean);

        /**
         * 删除账单返回
         * */
        void deleteBillResponse(DeleteBillResponseBean deleteBillResponseBean);

        /**
         * 还款返回
         * */
        void repayMentResponse(RepayMentResponseBean repayMentResponseBean);
    }

    interface presenter extends BasePresenter {

        /**
         * 获取账单列表请求
         * */
        void getBillListRequest(GetBillListRequestBean getBillListRequestBean);

        /**
         * 删除账单请求
         * */
        void deleteBillRequest(DeleteBillRequestBean deleteBillRequestBean);

        /**
         * 还款请求
         * */
        void repayMentRequest(RepayMentRequestBean repayMentRequestBean);
    }
}
