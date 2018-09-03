package com.loanuncle.gm.juke.bean.response;

import com.loanuncle.gm.juke.bean.BaseResponse;

/**
 * Created by GM on 2018/8/23.
 * @description 请求协议返回类
 */

public class AgreementResponseBean extends BaseResponse{


    /**
     * result : string
     */

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
