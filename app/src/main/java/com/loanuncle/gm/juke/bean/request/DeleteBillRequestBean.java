package com.loanuncle.gm.juke.bean.request;

import com.loanuncle.gm.juke.bean.BaseRequest;

/**
 * Created by GM on 2018/8/24.
 * @description 删除账单请求实体类
 */

public class DeleteBillRequestBean extends BaseRequest{

    public DeleteBillRequestBean() {
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
