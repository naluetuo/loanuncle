package com.loanuncle.gm.juke.bean.response;

import com.loanuncle.gm.juke.bean.BaseResponse;

/**
 * Created by GM on 2018/8/23.
 * @description 退出登录返回实体类
 */

public class LogoutResponseBean extends BaseResponse{

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
