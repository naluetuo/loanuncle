package com.loanuncle.gm.juke.bean.request;

import com.loanuncle.gm.juke.bean.BaseRequest;

/**
 * Created by GM on 2018/8/25.
 * @description 还款请求实体类
 */

public class RepayMentRequestBean extends BaseRequest{

    public RepayMentRequestBean() {
        super();
    }

    /**
     * billId : string
     */

    private String billId;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }
}
