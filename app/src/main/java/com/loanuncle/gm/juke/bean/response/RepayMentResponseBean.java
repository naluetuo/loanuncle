package com.loanuncle.gm.juke.bean.response;

import com.loanuncle.gm.juke.bean.BaseResponse;

/**
 * Created by GM on 2018/8/25.
 * @description 还款返回实体类
 */

public class RepayMentResponseBean extends BaseResponse{

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
