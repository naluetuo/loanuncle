package com.loanuncle.gm.juke.bean.response;

import com.loanuncle.gm.juke.bean.BaseResponse;

/**
 * Created by GM on 2018/9/16.
 * @description 意见反馈请求返回类
 */

public class FeedBackResponseBean extends BaseResponse{

    public FeedBackResponseBean() {
        super();
    }

    /**
     * result : false
     */

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
