package com.loanuncle.gm.juke.bean.request;

import com.loanuncle.gm.juke.bean.BaseRequest;

/**
 * Created by GM on 2018/8/25.
 * @description 获取已完成账单请求实体类
 */

public class GetBillDoneListRequestBean extends BaseRequest{

    public GetBillDoneListRequestBean() {
        super();
    }

    /**
     * pageSize : 0
     * requestPage : 0
     */

    private int pageSize;
    private int requestPage;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRequestPage() {
        return requestPage;
    }

    public void setRequestPage(int requestPage) {
        this.requestPage = requestPage;
    }
}
